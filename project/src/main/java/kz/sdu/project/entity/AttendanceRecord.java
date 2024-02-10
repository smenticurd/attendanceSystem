package kz.sdu.project.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attendance_record")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;


    @Column(name = "total_seating_hours")
    private Integer totalSeatingHours;

    @Column(name = "current_week")
    private Integer currentWeek;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Person person_att_record;

    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    private Schedule schedule_att_record;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendanceRecord that = (AttendanceRecord) o;

        return recordId != null ? recordId.equals(that.recordId) : that.recordId == null;
    }

    @Override
    public int hashCode() {
        return recordId != null ? recordId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "recordId=" + recordId +
                ", totalSeatingHours=" + totalSeatingHours +
                ", currentWeek=" + currentWeek +
                ", person_att_record=" + (person_att_record != null ? person_att_record.getId() : "null") +
                ", schedule_att_record=" + (schedule_att_record != null ? schedule_att_record.getScheduleId() : "null") +
                '}';
    }

}
