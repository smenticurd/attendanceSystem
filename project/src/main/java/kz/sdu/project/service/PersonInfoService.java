package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.PersonInfo;
import kz.sdu.project.repository.PersonInfoRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PersonInfoService {

    private final PersonInfoRepo personInfoRepo;

    @Autowired
    public PersonInfoService(PersonInfoRepo personInfoRepo) {
        this.personInfoRepo = personInfoRepo;
    }

    public PersonInfo findById(Integer id) {
        Optional<PersonInfo> result = personInfoRepo.findById(id);
        return result.orElse(null);
    }

    public PersonInfo findByPersonId(Integer personId) {
        Optional<PersonInfo> result = personInfoRepo.findByPersonId(personId);
        return result.orElse(null);
    }

    public List<PersonInfo> findAll() {
        return personInfoRepo.findAll();
    }

    public PersonInfo save(PersonInfo personInfo) {
        return personInfoRepo.save(personInfo);
    }

    public void delete(PersonInfo personInfo) {
        personInfoRepo.delete(personInfo);
    }

    public void deleteById(Integer id) {
        personInfoRepo.deleteById(id);
    }
}
