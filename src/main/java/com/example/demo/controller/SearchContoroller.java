package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class SearchContoroller {
	private final ProductRepository productRepository;
	
	@GetMapping("/search/category")
	public List<Product> getCategory(@RequestParam("category_id") String categoryId) {
		List<Product> list = productRepository.findAllByCategoryId(categoryId);;
		return list;
	}
	
	@GetMapping("/search/price")
	public List<Product> getPrice(@RequestParam("upper_price") Integer upperPrice,
								@RequestParam("lower_price") Integer lowerPrice) {
		List<Product> list = productRepository.findAllByPriceRenge(upperPrice, lowerPrice);
		return list;
	}
	
	@GetMapping("/search/keyword")
	public List<Product> getKeyword(@RequestParam("keyword") String keyword) {
		List<Product> list = null;
		return list;
	}
}
