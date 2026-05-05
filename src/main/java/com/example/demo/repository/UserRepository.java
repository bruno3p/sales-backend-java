package com.example.demo.repository;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserRepository {
	@Select("SELECT * FROM users")
	List<User> findAll();

	@Select("SELECT * FROM users WHERE email = #{email}")
	User findByEmail(String email);

	@Insert("INSERT INTO users (name, email, password) VALUES (#{name}, #{email}, #{password})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(User user);

	@Update("UPDATE users SET name=#{name}, email=#{email}, password=#{password} WHERE id=#{id}")
	void update(User user);

	@Delete("DELETE FROM users WHERE id=#{id}")
	void deleteById(Long id);
}
