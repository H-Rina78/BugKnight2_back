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
	
	@GetMapping("/search")
	public List<Product> getAllOrderByCategoryIdProductId() {
		List<Product> list = productRepository.getAllOrderByCategoryIdProductId();
		return list;
	}
	
	@GetMapping("/searchP")
	public List<Product> getAll(@RequestParam("upper_price") Integer upperPrice,
								@RequestParam("lower_price") Integer lowerPrice) {
		List<Product> list = productRepository.findAllByPriceRenge(upperPrice, lowerPrice);
		return list;
	}
	
	
	@GetMapping("/search/category")
	public List<Product> getCategory(@RequestParam("category_id") String categoryId) {
		List<Product> list = productRepository.findAllByCategoryId(categoryId);
		return list;
	}
	
	@GetMapping("/search/categoryP")
	public List<Product> getCategory(@RequestParam("category_id") String categoryId,
									@RequestParam("upper_price") Integer upperPrice,
									@RequestParam("lower_price") Integer lowerPrice) {
		List<Product> list = productRepository.findAllByCategoryIdByPriceRenge(categoryId, upperPrice, lowerPrice);
		return list;
	}
	
	@GetMapping("/search/keyword")
	public List<Product> getKeyword(@RequestParam("keyword") String keyword) {
		List<Product> list = productRepository.findAllByKeyword(keyword);
		return list;
	}
	 
	@GetMapping("/search/keywordP")
	public List<Product> getKeyword(@RequestParam("keyword") String keyword,
									@RequestParam("upper_price") Integer upperPrice,
									@RequestParam("lower_price") Integer lowerPrice) {
		List<Product> list = productRepository.findAllByKeywordByPriceRenge(keyword, upperPrice, lowerPrice);
		return list;
	}
	
	@GetMapping("/search/recommend")
	public List<Product> getRecommend(){
		List<Product> list = productRepository.findAllByRecommendFlg();
		return list;
	}
}
