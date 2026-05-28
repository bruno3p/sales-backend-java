package com.example.demo.service;

import com.example.demo.model.DoctorSettings;
import com.example.demo.repository.DoctorSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorSettingsService {

    @Autowired
    private DoctorSettingsRepository repository;

    public DoctorSettings getByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public void saveOrUpdate(DoctorSettings settings) {
        DoctorSettings existing = repository.findByDoctorId(settings.getDoctorId());
        if (existing != null) {
            settings.setId(existing.getId());
            repository.update(settings);
        } else {
            repository.save(settings);
        }
    }
}
