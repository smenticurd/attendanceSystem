package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "schedule")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;


    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "total_hours")
    private Integer totalHours;

    @OneToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_schedule;

    @OneToOne
    @JoinColumn(name = "class_room_id", referencedColumnName = "class_room_id")
    private ClassRoom classRoom_schedule;

    @OneToOne(mappedBy = "schedule_att_record", cascade = CascadeType.ALL)
    @JsonBackReference
    private AttendanceRecord attendanceRecord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule that = (Schedule) o;
        return Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime='" + startTime + '\'' +
                ", totalHours=" + totalHours +
                ", section_schedule=" + (section_schedule != null ? section_schedule.getSectionId() : "null") +
                ", classRoom_schedule=" + (classRoom_schedule != null ? classRoom_schedule.getClass_room_id() : "null") +
                '}';
    }


}