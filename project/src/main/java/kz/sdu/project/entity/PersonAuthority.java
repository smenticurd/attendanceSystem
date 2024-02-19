package kz.sdu.project.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "person_authority")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "person_authority_id",
            updatable = false
    )
    private Integer person_authority_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_login")
    private LocalDate last_login;

    @Column(
            name = "password_hash",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password_hash;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "password_refresh_date")
    private LocalDate password_refresh_date;

    @Column(
            name = "isRefreshed",
            nullable = false
    )
    private Boolean isRefreshed;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_person_auth;


    @OneToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "role_id")
    private Role role_person_auth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAuthority that = (PersonAuthority) o;
        return Objects.equals(person_authority_id, that.person_authority_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_authority_id);
    }

    @Override
    public String toString() {
        return "PersonAuthority{" +
                "id=" + person_authority_id +
                ", last_login=" + last_login +
                ", password_hash='" + password_hash + '\'' +
                ", active=" + active +
                ", password_refresh_date=" + password_refresh_date +
                ", isRefreshed=" + isRefreshed +
                '}';
    }



}
