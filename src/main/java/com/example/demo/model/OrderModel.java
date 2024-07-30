package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderModel {
	private LocalDate orderDate;
	
	private List<CartProductModel> product;
}
