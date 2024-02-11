package kz.sdu.project.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reason_for_absence")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReasonForAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Integer reasonId;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "document", columnDefinition = "TEXT")
    private String document;

    @Column(name = "status")
    private String status;

    @Column(name = "isAccepted")
    private Boolean isAccepted;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_reason_for_absence;

    @OneToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_reason_for_absence;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReasonForAbsence)) return false;
        ReasonForAbsence that = (ReasonForAbsence) o;
        return Objects.equals(reasonId, that.reasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reasonId);
    }

    @Override
    public String toString() {
        return "ReasonForAbsence{" +
                "reasonId=" + reasonId +
                ", description='" + description + '\'' +
                ", document='" + document + '\'' +
                ", status='" + status + '\'' +
                ", isAccepted=" + isAccepted +
                ", person_reason_for_absence=" + (person_reason_for_absence != null ? person_reason_for_absence.getId() : "null") +
                ", section_reason_for_absence=" + (section_reason_for_absence != null ? section_reason_for_absence.getSectionId() : "null") +
                '}';
    }

}

