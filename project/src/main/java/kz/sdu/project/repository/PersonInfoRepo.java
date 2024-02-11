package kz.sdu.project.repository;

import kz.sdu.project.entity.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonInfoRepo extends JpaRepository<PersonInfo, Integer> {
    @Query("select p from PersonInfo p where p.person_person_info.id = ?1")
    Optional<PersonInfo> findByPersonId(Integer id);
}
