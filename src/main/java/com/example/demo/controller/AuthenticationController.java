package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AuthenticationController {
	private final UserRepository userRepository;
	
	@GetMapping("/setCookie")
	public String setCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("newCookie", "cookieValue");
		cookie.setHttpOnly(true);
		cookie.setSecure(false);//httpsを使用している場合true
		cookie.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
        response.addCookie(cookie);
        return "Cookieを保存しました";
	}
	
	@PostMapping("/login")
	public boolean login(@RequestParam("id") String id,
						@RequestParam("password") String password) {
		User user = userRepository.loginCheck(id, password);
		boolean loginCheck = false;
		if(user != null) {
			loginCheck = true;
		}
		return loginCheck;
	}

}
