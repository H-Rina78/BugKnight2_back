package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.model.CartProductModel;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CartController {
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	
	@GetMapping("/bk/getCart")
	public List<CartProductModel> getCart(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		List<CartProductModel> list = new ArrayList<>();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("loginSession".equals(cookie.getName())) {
					String session = new String(Base64.getDecoder().decode(cookie.getValue()));
					System.out.println(session);
					User user = userRepository.isSession(session);
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
				}
			}
		}
		return list;
		
	}
	
	@PostMapping("/bk/setCart")
	public boolean setCart(HttpServletRequest request,
							@RequestParam("id") String id,
							@RequestParam("quantity") String quantity) {
		Cookie[] cookies = request.getCookies();
		boolean checkSetCart = false;
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("loginSession".equals(cookie.getName())) {
					String session = new String(Base64.getDecoder().decode(cookie.getValue()));
					System.out.println(session);
					User user = userRepository.isSession(session);
					if(user != null) {
						String product = user.getProducts();
						String ansProduct = "";
						String[] productList = product.split("_");
						for(int i = 0; i < productList.length; i++) {
							String products[] = productList[i].split(",");
							if(id == products[0]) {
								ansProduct += id + "," +  quantity;
								checkSetCart = true;
							} else {
								ansProduct += products[0] + "," + products[1]; 
							}
							if(i < productList.length - 1) {
								ansProduct += "_";
							}
						}
						if(!checkSetCart) {
							ansProduct += "_" + id + "," +  quantity;
							checkSetCart = true;
						}
						userRepository.updateProduct(ansProduct, user.getId());				}
				}
			}
		}
		return checkSetCart;
	}
}
