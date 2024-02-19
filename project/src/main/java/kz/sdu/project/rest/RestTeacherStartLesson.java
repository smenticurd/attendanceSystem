package kz.sdu.project.rest;

import kz.sdu.project.entity.*;
import kz.sdu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/startLesson")
public class RestTeacherStartLesson {
    private final ScheduleService scheduleService;
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;
    private final PersonService personService;
    private final AttendanceInfoService attendanceInfoService;

    Section section;
    Person teacher;
    Schedule schedule;
    Integer currentWeek = 1; // This will be fixed

    @Autowired
    public RestTeacherStartLesson(ScheduleService scheduleService, AttendanceRecordService attendanceRecordService, SectionService sectionService, PersonService personService, AttendanceInfoService attendanceInfoService) {
        this.scheduleService = scheduleService;
        this.attendanceRecordService = attendanceRecordService;
        this.sectionService = sectionService;
        this.personService = personService;
        this.attendanceInfoService = attendanceInfoService;
    }

    @PostMapping("/{teacherLogin}/{sectionName}")
    public String startLesson(@PathVariable("teacherLogin") String teacherLogin,
                              @PathVariable("sectionName") String sectionName) {

        section = sectionService.findByName(sectionName);
        schedule = scheduleService.findBySectionName(sectionName);
        teacher = personService.findByLogin(teacherLogin);

        if (section == null || teacher == null) {
            return "Person or Section is Wrong...";
        }

        teacherStartInfo();

        return "The Section is Successfully stated...";
    }

    // AttendanceRecord for Teacher & Student
    public void teacherStartInfo() {
        // Teacher set up process
        AttendanceRecord attendanceForTeacher = AttendanceRecord.builder()
                .person_att_record(teacher)
                .schedule_att_record(schedule)
                .total_present_hours(schedule.getTotalHours())
                .total_hours(schedule.getTotalHours())
                .currentWeek(currentWeek)
                .record_type("")
                .is_with_reason(false)
                .build();
        attendanceRecordService.save(attendanceForTeacher);
        initializeAttInfo(teacher,section);
        setUpAttInfo(teacher,section);


        // Students set up process...
        List<Person> students = section.getPersons().stream().filter(
                x -> !x.getLogin().equals(teacher.getLogin())
        ).toList();

        for (Person student : students) {
            AttendanceRecord attForStudent = AttendanceRecord.builder()
                    .person_att_record(student)
                    .schedule_att_record(schedule)
                    .total_present_hours(0)
                    .total_hours(schedule.getTotalHours())
                    .currentWeek(currentWeek)
                    .record_type("MANUALLY")
                    .is_with_reason(false)
                    .build();
            attendanceRecordService.save(attForStudent);
            initializeAttInfo(student,section);
            setUpAttInfo(student,section);
        }
    }

    // Modify AttendanceInfo for Person by List<AttendanceRecord>
    private void setUpAttInfo(Person curPerson, Section curSection) {
        AttendanceInfo attendanceInfo = attendanceInfoService.findByPersonIdAndSectionId(curPerson.getId(),
                curSection.getSectionId());
        List<AttendanceRecord> attendanceRecords = attendanceRecordService.findByPersonIdAndScheduleId(
                curPerson.getId(), schedule.getScheduleId());

        // Process calculation absences
        int total_hours = attendanceRecords.stream().mapToInt(AttendanceRecord::getTotal_hours).sum(),
                total_present_hours = attendanceRecords.stream().mapToInt(AttendanceRecord::getTotal_present_hours).sum(),
                is_with_reason = (int) attendanceRecords.stream().filter(AttendanceRecord::getIs_with_reason).count();


        //AttendanceInfo percent,full_time & reason_time in process...
        attendanceInfo.setPercent(total_hours - total_present_hours);
        attendanceInfo.setFull_time(total_hours);
        attendanceInfo.setReason_time(is_with_reason * schedule.getTotalHours());

        attendanceInfoService.save(attendanceInfo);
    }

    // Initialize AttendanceInfo for Person if it's absent
    private void initializeAttInfo(Person curPerson, Section curSection) {

        if (attendanceInfoService.findByPersonIdAndSectionId(curPerson.getId(),
                curSection.getSectionId()) == null) {
            AttendanceInfo initializeAttendanceInfo = AttendanceInfo.builder()
                    .person_attendanceInfo(curPerson)
                    .percent(0)
                    .full_time(0)
                    .reason_time(0)
                    .section_att_info(curSection)
                    .build();
            attendanceInfoService.save(initializeAttendanceInfo);
        }
    }
}
