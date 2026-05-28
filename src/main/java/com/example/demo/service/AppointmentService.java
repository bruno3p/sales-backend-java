package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public Appointment findById(Long id) {
        return repository.findById(id);
    }

    public List<Appointment> findByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public void create(Appointment appointment) {
        repository.save(appointment);
    }

    public void update(Appointment appointment) {
        repository.update(appointment);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void cancel(Long id) {
        Appointment appointment = repository.findById(id);
        if (appointment == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        // Parse date and time to LocalDateTime
        LocalDateTime appointmentDateTime = LocalDateTime.parse(
                appointment.getDate() + " " + appointment.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );

        LocalDateTime now = LocalDateTime.now();
        long hoursBetween = ChronoUnit.HOURS.between(now, appointmentDateTime);

        if (hoursBetween < 4) {
            throw new RuntimeException("Cancelamento só é permitido com no mínimo 4 horas de antecedência.");
        }

        appointment.setPatientId(null);
        appointment.setStatus("available");
        repository.update(appointment);
    }
}
