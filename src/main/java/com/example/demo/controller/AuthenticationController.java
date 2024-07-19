package com.example.demo.controller;

import java.util.Objects;

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
	
	@PostMapping("/registCheck")
	public String registCheck(@RequestParam("id") String id,
			             @RequestParam("mail") String mail) {
		String message = "";
		User userId = userRepository.isId(id);
		User userMail = userRepository.isMail(mail);
		if(Objects.nonNull(userId)) {
			message = "※このユーザIDは既に使用されています。";
		}
		
		if(Objects.nonNull(userMail)) {
			message = "※このメールアドレスは既に使用されています。";
		}
			
		System.out.println(message);
		return message;
	}
	
	
	
	@PostMapping("/regist")
	public String regist(@RequestParam("id") String id,
			             @RequestParam("lastName") String lastName,
			             @RequestParam("firstName") String firstName,
			             @RequestParam("address") String address,
			             @RequestParam("tel") String tel,
			             @RequestParam("mail") String mail,
			             @RequestParam("password") String password) {
		
		String message = "新規ユーザー登録しました。";
		userRepository.insertUser(id, lastName, firstName, mail, password, address, tel);
		
		System.out.println(message);
		return message;
	}

}
