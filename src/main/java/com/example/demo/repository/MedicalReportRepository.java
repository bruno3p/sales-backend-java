package com.example.demo.repository;

import com.example.demo.model.MedicalReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedicalReportRepository {

	@Select("SELECT id, name, details, patient_id, doctor_id, report_date as date, is_ai_summarized, ai_points_of_attention, original_file_name FROM medical_report")
	List<MedicalReport> findAll();

	@Insert("INSERT INTO medical_report (name, details, patient_id, doctor_id, report_date, is_ai_summarized, ai_points_of_attention, original_file_name) " +
			"VALUES (#{name}, #{details}, #{patientId}, #{doctorId}, #{date}, #{isAiSummarized}, #{aiPointsOfAttention}, #{originalFileName})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(MedicalReport report);

	@Select("SELECT id, name, details, patient_id, doctor_id, report_date as date, is_ai_summarized, ai_points_of_attention, original_file_name FROM medical_report WHERE id = #{id}")
	MedicalReport findById(Long id);

	@Update("UPDATE medical_report SET name = #{name}, details = #{details}, patient_id = #{patientId}, " +
			"doctor_id = #{doctorId}, report_date = #{date}, is_ai_summarized = #{isAiSummarized}, " +
			"ai_points_of_attention = #{aiPointsOfAttention}, original_file_name = #{originalFileName} WHERE id = #{id}")
	void update(MedicalReport report);

	@Delete("DELETE FROM medical_report WHERE id = #{id}")
	void delete(Long id);

	@Select("SELECT id, name, details, patient_id, doctor_id, report_date as date, is_ai_summarized, ai_points_of_attention, original_file_name FROM medical_report WHERE name LIKE CONCAT('%', #{name}, '%')")
	List<MedicalReport> findByNameContaining(String name);

	@Select("SELECT id, name, details, patient_id, doctor_id, report_date as date, is_ai_summarized, ai_points_of_attention, original_file_name FROM medical_report WHERE doctor_id = #{doctorId}")
	List<MedicalReport> findByDoctorId(Long doctorId);

	@Select("SELECT id, name, details, patient_id, doctor_id, report_date as date, is_ai_summarized, ai_points_of_attention, original_file_name FROM medical_report WHERE patient_id = #{patientId}")
	List<MedicalReport> findByPatientId(Long patientId);

}
