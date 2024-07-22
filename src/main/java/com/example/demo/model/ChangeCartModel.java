package com.example.demo.model;

import lombok.Data;

@Data
public class ChangeCartModel {
private String id;
	
	private String name;
	
	private String hiragana;
	
	private String katakana;
	
	private String categoryId;
	
	private String overview;
	
	private Integer price;
	
	private String imageName;
	
	private String recommendFlg;
	
	private Integer quantity;
}
