package com.example.demo.repository;

import com.example.demo.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepository {

	@Select("SELECT * FROM product")
	List<Product> findAll();

	@Insert("INSERT INTO product (name, price, stock) VALUES (#{name}, #{price}, #{stock})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Product product);

	@Select("SELECT * FROM product WHERE id = #{id}")
	Product findById(Long id);

	@Update("UPDATE product SET name = #{name}, price = #{price}, stock = #{stock} WHERE id = #{id}")
	void update(Product product);

	@Delete("DELETE FROM product WHERE id = #{id}")
	void delete(Long id);

	@Select("SELECT * FROM product WHERE name LIKE CONCAT('%', #{name}, '%')")
	List<Product> findByNameContaining(String name);

}
