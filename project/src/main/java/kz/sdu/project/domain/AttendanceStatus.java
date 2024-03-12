package kz.sdu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public enum AttendanceStatus {
    ABSENT_STATUS("ABSENT"),
    PRESENT_STATUS("PRESENT"),
    WITH_REASON_STATUS("WITH_REASON");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return this.status;
    }
}

