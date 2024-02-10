package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "section")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "quota")
    private Integer quota;

    @Column(name = "count")
    private Integer count;

    @Column(name = "type")
    private String type;

    @Column(name = "related_section_name")
    private String relatedSectionName;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course_section;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_section;

    @OneToOne(mappedBy = "section_schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Schedule schedule;

    @OneToOne(mappedBy = "section_reason_for_absence", cascade = CascadeType.ALL)
    @JsonBackReference
    private ReasonForAbsence reasonForAbsence;

    @OneToOne(mappedBy = "section_att_info", cascade = CascadeType.ALL)
    @JsonBackReference
    private AttendanceInfo attendanceInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return Objects.equals(sectionId, section.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId);
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", name='" + name + '\'' +
                ", quota=" + quota +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", relatedSectionName='" + relatedSectionName + '\'' +
                ", course_section=" + (course_section != null ? course_section.getCourse_id() : "null") +
                ", person_section=" + (person_section != null ? person_section.getId() : "null") +
                '}';
    }

}