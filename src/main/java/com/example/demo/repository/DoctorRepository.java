package com.example.demo.repository;

import com.example.demo.model.Doctor;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DoctorRepository {
	@Select("SELECT * FROM doctor")
	List<Doctor> findAll();

	@Select("SELECT * FROM doctor WHERE email = #{email}")
	Doctor findByEmail(String email);

	@Insert("INSERT INTO doctor (name, email, password) VALUES (#{name}, #{email}, #{password})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Doctor doctor);

	@Update("UPDATE doctor SET name=#{name}, email=#{email}, password=#{password} WHERE id=#{id}")
	void update(Doctor doctor);

	@Delete("DELETE FROM doctor WHERE id=#{id}")
	void deleteById(Long id);
}
