package com.example.demo.repository;

import com.example.demo.model.DoctorSettings;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DoctorSettingsRepository {

    @Select("SELECT id, doctor_id as doctorId, work_days as workDays, start_time as startTime, end_time as endTime, " +
            "has_lunch_break as hasLunchBreak, lunch_start as lunchStart, lunch_end as lunchEnd FROM doctor_settings WHERE doctor_id = #{doctorId}")
    DoctorSettings findByDoctorId(Long doctorId);

    @Insert("INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) " +
            "VALUES (#{doctorId}, #{workDays}, #{startTime}, #{endTime}, #{hasLunchBreak}, #{lunchStart}, #{lunchEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(DoctorSettings doctorSettings);

    @Update("UPDATE doctor_settings SET work_days = #{workDays}, start_time = #{startTime}, end_time = #{endTime}, " +
            "has_lunch_break = #{hasLunchBreak}, lunch_start = #{lunchStart}, lunch_end = #{lunchEnd} WHERE doctor_id = #{doctorId}")
    void update(DoctorSettings doctorSettings);
}
