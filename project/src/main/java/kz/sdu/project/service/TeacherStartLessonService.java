package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBody3DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherStartLessonService {
    private final PersonService personService;
    @Autowired
    public TeacherStartLessonService(PersonService personService) {
        this.personService = personService;
    }
    public Map<String, String> start(@Valid RequestBody3DTO requestBody3DTO) {

        Map<String, String> codeMap = new HashMap<>();

        return codeMap;
    }
}
