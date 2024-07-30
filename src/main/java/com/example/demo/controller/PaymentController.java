package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OrderHistory;
import com.example.demo.repository.OrderRepository;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class PaymentController {
	private final OrderRepository orderRepository;

	@PostMapping("/orderHistory")
	public List<OrderHistory> getOrderHistory(@RequestParam("id") String userId) {
		List<OrderHistory> historyList = new ArrayList<>();
		historyList = orderRepository.getAllOrderByUserId(userId);
		
		return historyList;
	}
	
	@PostMapping("/insertOrder")
	public void insertOrder() {
		orderRepository.insertOrder("orderHist0726", "VE0001,1_VE0002,2");
	}
}
