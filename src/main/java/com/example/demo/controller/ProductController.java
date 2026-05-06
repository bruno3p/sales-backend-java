package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> listAll() {
		return productService.getAllProducts();
	}

	@PostMapping
	public String create(@Valid @RequestBody Product product) {
		productService.saveProduct(product);
		return "Produto salvo com sucesso!";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, @RequestBody Product product) {
		productService.updateProduct(id, product);
		return "Produto atualizado com sucesso!";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "Produto removido com sucesso!";
	}

	@GetMapping("/search")
	public List<Product> search(@RequestParam String name) {
		return productService.searchProducts(name);
	}

	@GetMapping("/category")
	public List<Product> findByCategory(@RequestParam String categoryName) {
		return productService.findProductsByCategory(categoryName);
	}

}