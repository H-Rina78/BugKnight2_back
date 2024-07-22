package com.example.demo.model;

import lombok.Data;

@Data
public class CartProductModel {
	
	private String id;
	
	private String name;
	
	private String categoryId;
	
	private Integer price;
	
	private String imageName;
	
	private Integer quantity;
}
