package kz.sdu.project.service;

import kz.sdu.project.dto.AttendanceStatusDetailDto;
import kz.sdu.project.dto.AttendanceStatusDto;
import kz.sdu.project.dto.RequestBody2DTO;
import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static kz.sdu.project.domain.ActionStatus.*;
import static kz.sdu.project.domain.Constants.*;
@Service
@Slf4j
public class StudentAttStatusService {

    private final PersonService personService;
    private final AttendanceInfoService attendanceInfoService;
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;

    @Autowired
    public StudentAttStatusService(PersonService personService, AttendanceInfoService attendanceInfoService, AttendanceRecordService attendanceRecordService, SectionService sectionService) {
        this.personService = personService;
        this.attendanceInfoService = attendanceInfoService;
        this.attendanceRecordService = attendanceRecordService;
        this.sectionService = sectionService;
    }

    public Map<String, AttendanceStatusDto> attStatusByAll(RequestBodyDTO requestBodyDTO) {

        String login = requestBodyDTO.getLogin();
        Person student = personService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Student with username %s not found", login)));
        Map<String, AttendanceStatusDto> finalAttMapList = new HashMap<>();

        List<AttendanceInfo> attList = attendanceInfoService.findByPersonId(student.getId());
        if (attList == null || attList.isEmpty()) {
            log.info("No attendance information found for the student with ID {}", student.getId());
            return finalAttMapList;
        }

        attList.forEach(attendanceInfo -> updateAttendanceStatus(finalAttMapList, attendanceInfo));

        log.info("Completed AttendanceStatusAll for {}" , student);
        return finalAttMapList;
    }

    public Map<String, List<AttendanceStatusDetailDto>> attStatusBySection(RequestBody2DTO requestBodyDTO) {

        String login = requestBodyDTO.getLogin();
        Person student = personService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Student with username %s not found", login)));
        String[] sections = requestBodyDTO.getSectionNames().split("[:;]");
        Map<String, List<AttendanceStatusDetailDto>> attMap = new HashMap<>();
        System.out.println("sections.length" + sections.length);
        for(String section : sections) {
            System.out.println("Before Sections are " + section);
            if (section.isEmpty()) continue;
            updateAttendanceDetailStatus(attMap, section, student);
            System.out.println("Sections are " + section);
        }


        log.info("Completed AttendanceStatusAllBySection for {}" , student);
        return attMap;
    }

    private void updateAttendanceDetailStatus(Map<String, List<AttendanceStatusDetailDto>> attMap, String sectionName, Person student) {

        Section section = sectionService.findByName(sectionName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with name %s not found", sectionName)));
        Schedule schedule = section.getSchedule();
        String lecType = section.getType();

        List<AttendanceRecord> attRecords = attendanceRecordService
                .findByPersonIdAndScheduleId(student.getId(), schedule.getScheduleId());
        List<AttendanceStatusDetailDto> attDetList = new ArrayList<>();
        attRecords.forEach(attendanceRecord -> updateAttDetList(attDetList,attendanceRecord,schedule));

        attMap.put(lecType, attDetList);
    }

    private void updateAttDetList(List<AttendanceStatusDetailDto> attDetList, AttendanceRecord attendanceRecord, Schedule schedule) {
        int totalHours = attendanceRecord.getTotal_hours(),
                presentHours = attendanceRecord.getTotal_present_hours();
        boolean isWithReason = attendanceRecord.getIs_with_reason();
        LocalDate date = getCurrentLocalDate(attendanceRecord.getCurrentWeek(), schedule.getDayOfWeek());
        String place = schedule.getClassRoom_schedule().getRoom_number();

        for (int hour = 0; hour < totalHours; hour++) {
            String attStatus = determineAttendanceStatus(presentHours--,isWithReason);
            String time = formatHour(schedule.getStartTime(), hour);
            addAttendanceDetailDto(attDetList, date, place, attStatus, time);
        }
    }

    private String determineAttendanceStatus(int presentHours, boolean isWithReason) {
        if (isWithReason) {
            return WITH_REASON_STATUS.toString();
        } else if (presentHours > 0) {
            return PRESENT_STATUS.toString();
        } else {
            return ABSENT_STATUS.toString();
        }
    }

    private String formatHour(String startTime, int hourIncrement) {
        int startsAt = Integer.parseInt(startTime.split("[.;:]")[0]);
        return (startsAt + hourIncrement) + ":00";
    }

    private void addAttendanceDetailDto(List<AttendanceStatusDetailDto> list, LocalDate date, String place, String status, String time) {
        list.add(AttendanceStatusDetailDto.builder()
                .date(date)
                .place(place)
                .attStatus(status)
                .hour(time)
                .build());
    }

    private LocalDate getCurrentLocalDate(int currentWeek, Integer dayOfWeek) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(START_DATE_FOR_SEMESTER, formatter);

        return startDate.plusWeeks(currentWeek - 1).plusDays(dayOfWeek - 1);
    }

    private void updateAttendanceStatus(Map<String, AttendanceStatusDto> attMapList, AttendanceInfo attendanceInfo) {

        String sectionName = attendanceInfo.getSection_att_info().getName();
        Integer hours = attendanceInfo.getFull_time(),
                absentHours = attendanceInfo.getPercent(),
                reasonHours = attendanceInfo.getReason_time(),
                presentHours = hours - absentHours - reasonHours;

        Optional<String> matchingPrefix = findMatchingPrefixInSectionName(attMapList.keySet(), sectionName);
        matchingPrefix.ifPresentOrElse(
                prevSectionName -> {
                    AttendanceStatusDto attDto = attMapList.remove(prevSectionName);
                    attDto.setHours(attDto.getHours() + hours);
                    attDto.setPresentHours(attDto.getPresentHours() + presentHours);
                    attDto.setAbsentHours(attDto.getAbsentHours() + absentHours);
                    attDto.setReasonHours(attDto.getReasonHours() + reasonHours);
                    attDto.setAbsenceLimit(attDto.getAbsenceLimit() + absentHours * 2);
                    attMapList.put(prevSectionName + ":" + sectionName, attDto);
                },
                () -> {
                    Course course = attendanceInfo.getSection_att_info().getCourse_section();
                    attMapList.put(sectionName, AttendanceStatusDto.builder()
                            .code(course.getCode())
                            .courseName(course.getName())
                            .hours(hours)
                            .presentHours(presentHours)
                            .absentHours(absentHours)
                            .reasonHours(reasonHours)
                            .absenceLimit(absentHours * 2)
                            .build());
                }
        );
    }

    private Optional<String> findMatchingPrefixInSectionName(Set<String> sectionNames, String sectionName) {
        return sectionNames.stream()
                .filter(prevSectionName -> shareSamePrefixName(prevSectionName,sectionName))
                .findFirst();
    }

    private boolean shareSamePrefixName(String sName, String name) {
        String prevSectionName = extractPrefix(sName);
        String sectionName = extractPrefix(name);
        return Objects.equals(prevSectionName, sectionName);
    }

    private String extractPrefix(String name) {
        int dotIndex = name.indexOf(".");
        return dotIndex != -1 ? name.substring(0, dotIndex) : name;
    }
}
