package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //JSONでオブジェクトを返すために余計なやつをシリアライズしない
public class Product {
	
	@Id
	@Column(name = "product_id")
	private String id;
	
	@Column(name = "product_name")
	private String name;
	
	@Column(name = "product_hiragana")
	private String hiragana;
	
	@Column(name = "product_katakana")
	private String katakana;
	
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "overview")
	private String overview;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "recommend_flg")
	private String recommendFlg;
}
