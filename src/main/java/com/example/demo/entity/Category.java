package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //JSONでオブジェクトを返すために余計なやつをシリアライズしない
public class Category {
	
	@Id
	@Column(name = "category_id")
	private int Integer;
	
	@Column(name = "category_name")
	private String name;
}
