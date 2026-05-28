package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private Long id;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long doctorId;

    private Long patientId; // null se estiver disponível

    @NotNull(message = "A data do agendamento é obrigatória")
    private LocalDate date;

    @NotBlank(message = "A hora do agendamento é obrigatória")
    private String time;

    @NotBlank(message = "O status do agendamento é obrigatório")
    private String status; // 'available' ou 'booked'
}
