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

	@Select("SELECT * FROM patient WHERE id = #{id}")
	Patient findById(Long id);

	@Insert("INSERT INTO patient (name, email, password, avatar) VALUES (#{name}, #{email}, #{password}, #{avatar})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Patient patient);

	@Update("UPDATE patient SET name=#{name}, email=#{email}, password=#{password}, avatar=#{avatar} WHERE id=#{id}")
	void update(Patient patient);

	@Delete("DELETE FROM patient WHERE id=#{id}")
	void deleteById(Long id);
}
