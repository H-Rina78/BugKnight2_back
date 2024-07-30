package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Product;
import com.example.demo.model.CartProductModel;
import com.example.demo.model.OrderModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class PaymentController {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@PostMapping("/orderHistory")
	public List<OrderModel> getOrderHistory(@RequestParam("id") String userId) {
		List<OrderModel> orderList = new ArrayList<>();
		List<OrderHistory> historyList = orderRepository.getAllOrderByUserId(userId);
		if(Objects.nonNull(historyList)) {
			for(int i = 0; i < historyList.size(); i++) {
				OrderHistory order = historyList.get(i);
				String product = order.getProductList();
				String[] productList = product.split("_");
				OrderModel orderModel = new OrderModel();
				List<CartProductModel> orderProductList = new ArrayList<>();
				for(int j = 0; j < productList.length; j++) {
					String products[] = productList[j].split(",");
					Product newProduct = productRepository.getReferenceById(products[0]);						
					CartProductModel cartProduct = newProduct.getCartProductModel();
					cartProduct.setQuantity(Integer.parseInt(products[1]));
					orderProductList.add(cartProduct);
				}
				orderModel.setOrderDate(order.getOrderDate());
				orderModel.setProduct(orderProductList);
				orderList.add(orderModel);
			}
		}
		
		return orderList;
	}
	
	@PostMapping("/insertOrder")
	public void insertOrder(@RequestParam("id") String id) {
		String productList = orderRepository.getCartProduct(id);
		orderRepository.insertOrder(id, productList);
		orderRepository.clearCart(id);
	}
}
