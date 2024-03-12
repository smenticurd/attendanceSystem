package kz.sdu.project.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestBody2DTO {
    @NotBlank(message = "Student login shouldn't be empty...")
    private String login;
    @NotBlank(message = "SectionNames shouldn't be empty...")
    private String sectionNames;
}
