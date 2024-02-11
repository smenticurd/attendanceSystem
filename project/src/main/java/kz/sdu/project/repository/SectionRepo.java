package kz.sdu.project.repository;

import kz.sdu.project.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section, Integer> {
    Optional<Section> findByName(String name);
}
