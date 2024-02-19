package kz.sdu.project.rest;

import kz.sdu.project.entity.AttendanceInfo;
import kz.sdu.project.entity.Course;
import kz.sdu.project.entity.Person;
import kz.sdu.project.service.AttendanceInfoService;
import kz.sdu.project.service.PersonService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/")
public class RestStudentAttStatusSeeAndCheckIn {

    private final AttendanceInfoService attendanceInfoService;
    private final PersonService personService;

    Person person;
    @Autowired
    public RestStudentAttStatusSeeAndCheckIn(AttendanceInfoService attendanceInfoService, PersonService personService) {
        this.attendanceInfoService = attendanceInfoService;
        this.personService = personService;
    }

    @GetMapping("/{login}")
    public String allCoursesAttendance(@PathVariable("login") String login) {

        person = personService.findByLogin(login);

        if (person == null) {
            return null;
        }

        List<AttendanceInfo> attendanceInfoList = attendanceInfoService.findAllByPersonLogin(login);

        Map<String, AttStatus> mapAttInfo = new HashMap<>();

        attendanceInfoList.forEach(x -> mapping(x, mapAttInfo));
    }

    public void mapping(AttendanceInfo x, Map<String, AttStatus> mapAttInfo) {

        Course currentCourse = x.getSection_att_info().getCourse_section();
        String courseName = currentCourse.getName(),
                courseCode = currentCourse.getCode();
        int absentHours = x.getPercent();
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
class AttStatus {
    String courseName,courseCode;
    int allHours, presentHours, absentHours, illnessHours;
}
