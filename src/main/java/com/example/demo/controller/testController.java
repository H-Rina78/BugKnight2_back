package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class testController {
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	
	@GetMapping("/test")
	public Category a() {
		Category category = categoryRepository.getReferenceById(1);
		return category;
	}
	
	@GetMapping("/test2")
	public Product b() {
		Product product = productRepository.getReferenceById("DR0004");
		return product;
	}
}
