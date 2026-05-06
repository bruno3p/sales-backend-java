package com.example.demo.repository;

import com.example.demo.model.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface CategoryRepository {
	@Insert("INSERT INTO category (name) VALUES (#{name})")
	void save(Category category);

	@Select("SELECT * FROM category")
	List<Category> findAll();
}
