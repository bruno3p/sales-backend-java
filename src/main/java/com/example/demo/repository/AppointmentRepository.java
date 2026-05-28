package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentRepository {

    @Select("SELECT id, doctor_id as doctorId, patient_id as patientId, appointment_date as date, " +
            "appointment_time as time, status FROM appointment")
    List<Appointment> findAll();

    @Select("SELECT id, doctor_id as doctorId, patient_id as patientId, appointment_date as date, " +
            "appointment_time as time, status FROM appointment WHERE id = #{id}")
    Appointment findById(Long id);

    @Select("SELECT id, doctor_id as doctorId, patient_id as patientId, appointment_date as date, " +
            "appointment_time as time, status FROM appointment WHERE doctor_id = #{doctorId}")
    List<Appointment> findByDoctorId(Long doctorId);

    @Insert("INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) " +
            "VALUES (#{doctorId}, #{patientId}, #{date}, #{time}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Appointment appointment);

    @Update("UPDATE appointment SET doctor_id = #{doctorId}, patient_id = #{patientId}, " +
            "appointment_date = #{date}, appointment_time = #{time}, status = #{status} WHERE id = #{id}")
    void update(Appointment appointment);

    @Delete("DELETE FROM appointment WHERE id = #{id}")
    void delete(Long id);
}
