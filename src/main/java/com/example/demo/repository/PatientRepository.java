package com.example.demo.repository;

import com.example.demo.model.Patient;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PatientRepository {
	@Select("SELECT * FROM patient")
	List<Patient> findAll();

	@Select("SELECT * FROM patient WHERE email = #{email}")
	Patient findByEmail(String email);

	@Insert("INSERT INTO patient (name, email, password) VALUES (#{name}, #{email}, #{password})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Patient patient);

	@Update("UPDATE patient SET name=#{name}, email=#{email}, password=#{password} WHERE id=#{id}")
	void update(Patient patient);

	@Delete("DELETE FROM patient WHERE id=#{id}")
	void deleteById(Long id);
}
