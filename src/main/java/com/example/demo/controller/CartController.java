package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.model.CartProductModel;
import com.example.demo.model.ConfirmationUtil;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CartController {
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	
	@PostMapping("/bk/getCart")
	public List<CartProductModel> getCart(@RequestParam("session") String session) {
		List<CartProductModel> list = new ArrayList<>();
		String sessionId = ConfirmationUtil.decodeSessionId(session);
		User user = userRepository.isSession(sessionId);
		if(user != null && user.getProducts() != null) {
			String product = user.getProducts();
			String[] productList = product.split("_");
			for(int i = 0; i < productList.length; i++) {
				String products[] = productList[i].split(",");
				Product newProduct = productRepository.getReferenceById(products[0]);						
				CartProductModel cartProduct = newProduct.getCartProductModel();
				cartProduct.setQuantity(Integer.parseInt(products[1]));
				list.add(cartProduct);
			}
			return list;
		}
		return list;
		
	}
	
	@PostMapping("/bk/setCart")
	public String setCart(@RequestParam("id") String id,
							@RequestParam("quantity") String quantity,
							@RequestParam("session") String session) {
		boolean checkSetCart = false;
		String message = "false";
		String sessionId = ConfirmationUtil.decodeSessionId(session);
		User user = userRepository.isSession(sessionId);
		if(user != null) {
			String product = user.getProducts();
			String ansProductList = "";
			if(Objects.nonNull(product) && !"".equals(product)) {
				String[] productList = product.split("_");
				System.out.println(productList.length);
				if(productList.length > 19) {
					return "20length";
				}
				for(int i = 0; i < productList.length; i++) {
					String[] products = productList[i].split(",");
					if(id.equals(products[0])) {
						ansProductList += id + "," + quantity;
						checkSetCart = true;
						message = "true";
					} else {
						ansProductList += products[0] + "," + products[1];
					}
					if(i < productList.length - 1) {
						ansProductList += "_";
					}
				}
				if(!checkSetCart) {
					ansProductList += "_" + id + "," + quantity;
					checkSetCart = true;
					message = "true";
				}
			} else {
				ansProductList += id + "," + quantity;
				checkSetCart = true;
				message = "true";
			}
			userRepository.updateProduct(ansProductList, user.getId());
		}
		return message;
	}
	
	@PostMapping("/bk/changeCart")
	public boolean changeCart(@RequestParam("products") String productList,
								@RequestParam("session") String session) {
		boolean checkSetCart = false;
		String sessionId = ConfirmationUtil.decodeSessionId(session);
		User user = userRepository.isSession(sessionId);
		if(user != null) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(productList);
	        try {
	        	String ansString = "";
	        	List<CartProductModel> cartProductList = mapper.readValue(productList, new TypeReference<List<CartProductModel>>() {});
	        	for(int i = 0; i < cartProductList.size(); i++) {
	        		 CartProductModel product = cartProductList.get(i);
	        		 ansString += product.getId() + "," + product.getQuantity();
	        		 if(i < cartProductList.size() - 1) {
	        			 ansString += "_";
	        		 }
	        	}
	        	userRepository.updateProduct(ansString, user.getId());
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		}
		return checkSetCart;
	}
}
