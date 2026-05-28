package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSettings {
    private Long id;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long doctorId;

    private String workDays; // Ex: "1,2,3,4,5"
    private String startTime;
    private String endTime;
    private Boolean hasLunchBreak;
    private String lunchStart;
    private String lunchEnd;
}
