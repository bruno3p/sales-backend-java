package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public void saveProduct(Product product) {
		if (product.getPrice() < 0) {
			throw new IllegalArgumentException("O preço não pode ser negativo!");
		}
		productRepository.save(product);
	}

	public void updateProduct(Long id, Product product) {
		Product existingProduct = productRepository.findById(id);
		if (existingProduct == null) {
			throw new RuntimeException("Produto não encontrado com id: " + id);
		}
		product.setId(id);
		productRepository.update(product);
	}

	public void deleteProduct(Long id) {
		productRepository.delete(id);
	}

	public List<Product> searchProducts(String name) {
		return productRepository.findByNameContaining(name);
	}

	public List<Product> findProductsByCategory(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}

}