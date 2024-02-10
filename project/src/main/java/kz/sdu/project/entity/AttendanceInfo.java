package kz.sdu.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attendance_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_info_id")
    private Integer attendance_info_id;


    @Column(name = "record_type", nullable = false)
    private String recordType;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_att_info;

    @OneToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_att_info;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendanceInfo that = (AttendanceInfo) o;

        return attendance_info_id != null ? attendance_info_id.equals(that.attendance_info_id) : that.attendance_info_id == null;
    }

    @Override
    public int hashCode() {
        return attendance_info_id != null ? attendance_info_id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AttendanceInfo{" +
                "attendance_info_id=" + attendance_info_id +
                ", recordType='" + recordType + '\'' +
                ", percent=" + percent +
                ", person_att_info=" + (person_att_info != null ? person_att_info.getId() : "null") +
                ", section_att_info=" + (section_att_info != null ? section_att_info.getSectionId() : "null") +
                '}';
    }

}
