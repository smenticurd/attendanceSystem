package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "specialty")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "specialty_id",
            updatable = false
    )
    private Integer specialty_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToOne(mappedBy = "specialty_course", cascade = CascadeType.ALL)
    @JsonBackReference
    private Course course;

    @OneToOne(mappedBy = "specialty_person_info", cascade = CascadeType.ALL)
    @JsonBackReference
    private PersonInfo personInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialty)) return false;
        Specialty that = (Specialty) o;
        return Objects.equals(specialty_id, that.specialty_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty_id);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialty_id=" + specialty_id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
