package kz.sdu.project.service;

import kz.sdu.project.adapter.PersonAdapter;
import kz.sdu.project.dto.AuthDto;
import kz.sdu.project.dto.RegistrationDto;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.security.JwtUtil;
import kz.sdu.project.security.PersonDetailsService;
import kz.sdu.project.utils.RegistrationValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RegistrationValidation registrationValidation;
    private final PersonService personService;
    private final PersonAuthorityService personAuthorityService;
    private final RoleService roleService;
    private final PersonInfoService personInfoService;
    private final SpecialityService specialityService;

    public Map<String, String> login(AuthDto authDto) {
        UserDetails userDetails = personDetailsService.loadUserByUsername(authDto.getLogin());
        if (passwordEncoder.matches(authDto.getPassword(), userDetails.getPassword())) {
            return Map.of("token", jwtUtil.generateToken(authDto.getLogin()));
        }
        return Map.of("Message", "Incorrect credentials");
    }

    @Transactional
    public ResponseEntity<HttpStatus> register(RegistrationDto registrationDto) {

        String spe_code = registrationDto.getSpecialityCode();
        Speciality speciality = specialityService.findByCode(spe_code)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Speciality with code %s not found...",spe_code)));
        String role = "ROLE_STUDENT";
        Role student = roleService.findByRole(role)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with %s role found", role)));

        try {
            log.info("Start registration of student");
            registrationValidation.validation(registrationDto);

            Person person = PersonAdapter.toEntity(registrationDto);
            person.setRolePerson(Set.of(student));

            PersonInfo personInfo = PersonAdapter.toEntityPersonInfo(registrationDto);
            personInfo.setPerson_person_info(person);
            personInfo.setSpecialty_person_info(speciality);

            PersonAuthority personAuthority = PersonAdapter.toEntityPersonAuth(registrationDto);
            personAuthority.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
            personAuthority.setPerson(person);

            personService.save(person);
            personAuthorityService.save(personAuthority);
            personInfoService.save(personInfo);
            log.info("Successfully registered");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}