package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<Role> role_person;

    @OneToOne(mappedBy = "person_person_auth", cascade = CascadeType.ALL)
    @JsonBackReference
    private PersonAuthority personAuthority;

    @OneToOne(mappedBy = "person_person_info", cascade = CascadeType.ALL)
    @JsonBackReference
    private PersonInfo personInfo;

    @OneToOne(mappedBy = "person_section", cascade = CascadeType.ALL)
    @JsonBackReference
    private Section section;

    @OneToOne(mappedBy = "person_reason_for_absence", cascade = CascadeType.ALL)
    @JsonBackReference
    private ReasonForAbsence reasonForAbsence;

    @OneToOne(mappedBy = "person_att_record", cascade = CascadeType.ALL)
    @JsonBackReference
    private AttendanceRecord attendanceRecord;

    @OneToOne(mappedBy = "person_att_info", cascade = CascadeType.ALL)
    @JsonBackReference
    private AttendanceInfo attendanceInfo;

    @OneToOne(mappedBy = "person_course", cascade = CascadeType.ALL)
    @JsonBackReference
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                // Roles, personAuthority, personInfo, and other relations are omitted to prevent excessive verbosity and potential recursion
                '}';
    }



}
