package kz.sdu.project.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestBodyDTO {
    @NotBlank(message = "Student login shouldn't be empty...")
    private String login;
}