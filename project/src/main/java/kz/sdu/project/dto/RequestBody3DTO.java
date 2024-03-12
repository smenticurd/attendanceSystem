package kz.sdu.project.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestBody3DTO {
    @NotBlank(message = "Teacher login shouldn't be empty...")
    private String login;
    @NotBlank(message = "SectionName shouldn't be empty...")
    private String sectionName;
}
