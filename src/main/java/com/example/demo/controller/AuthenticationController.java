package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@CrossOrigin
	@GetMapping("/setCookie")
	public String setCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("newCookie", "cookieValue");
		cookie.setHttpOnly(true);
		cookie.setSecure(false);//httpsを使用している場合true
		cookie.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
        response.addCookie(cookie);
        return "Cookieを保存しました";
	}
	
	@GetMapping("/login")
	public String login() {
		User user = userRepository.loginCheck("kouno123", "qwerty");
		String a = null;
		if(user != null) {
			a = "ログイン出来ました";
		} else {
			a = "ログインできませんでした";
		}
		return a;
	}

}
