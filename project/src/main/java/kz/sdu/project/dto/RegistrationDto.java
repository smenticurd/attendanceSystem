package kz.sdu.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
// TODO : описать поля ошибок
public class RegistrationDto {
    @NotBlank(message = "email must be not blank")
    @Email(message = "Email format is incorrect. Example: user123@mail.ru, user123@yandex.ru, user@gmail.com")
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8)
    private String password;
    @NotNull
    @NotBlank
    @NotEmpty
    private String firstname;
    @NotNull
    @NotBlank
    @NotEmpty
    private String lastname;
    @NotNull
    @NotBlank
    @NotEmpty
    private String middlename;
    @NotNull
    @NotBlank
    @NotEmpty
    private String courseYear;
    @NotNull
    private Boolean gender;
    @NotNull
    @NotBlank
    @NotEmpty
    private String specialityCode;

    private String telephone;

    private String birthDate;

}
