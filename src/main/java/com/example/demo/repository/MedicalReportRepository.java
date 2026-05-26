package com.example.demo.repository;

import com.example.demo.model.MedicalReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedicalReportRepository {

	@Select("SELECT * FROM medical_report")
	List<MedicalReport> findAll();

	@Insert("INSERT INTO medical_report (name, details, category_id, patient_id, doctor_id) VALUES (#{name}, #{details}, #{categoryId}, #{patientId}, #{doctorId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(MedicalReport report);

	@Select("SELECT * FROM medical_report WHERE id = #{id}")
	MedicalReport findById(Long id);

	@Update("UPDATE medical_report SET name = #{name}, details = #{details}, category_id = #{categoryId}, patient_id = #{patientId}, doctor_id = #{doctorId} WHERE id = #{id}")
	void update(MedicalReport report);

	@Delete("DELETE FROM medical_report WHERE id = #{id}")
	void delete(Long id);

	@Select("SELECT * FROM medical_report WHERE name LIKE CONCAT('%', #{name}, '%')")
	List<MedicalReport> findByNameContaining(String name);

	@Select("SELECT m.* FROM medical_report m " +
			"JOIN category c ON m.category_id = c.id " +
			"WHERE c.name = #{categoryName}")
	List<MedicalReport> findByCategoryName(String categoryName);

}
