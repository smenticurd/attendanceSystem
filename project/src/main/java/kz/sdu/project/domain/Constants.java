package kz.sdu.project.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Constants {
    public static final String START_DATE_FOR_SEMESTER = "22.01.2024";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final Integer SIX_SIZED_SECRET_CODE = 6;
    public static final Boolean USE_LETTERS_IN_SECRET_CODE = true;
    public static final Boolean USE_NUMBERS_IN_SECRET_CODE = true;
}
