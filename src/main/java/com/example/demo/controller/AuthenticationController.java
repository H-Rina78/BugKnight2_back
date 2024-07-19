package com.example.demo.controller;

import java.util.Base64;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AuthenticationController {
	private final UserRepository userRepository;
	
	@GetMapping("/bk/setCookie")
	public String setCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("checkCookie", "true");
		Cookie cookie2 = new Cookie("loginInfo", "0");
		cookie.setPath("/");
//		cookie.setHttpOnly(true);
		cookie.setSecure(false);//httpsを使用している場合true
		cookie.setDomain("localhost");
		cookie.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
		cookie2.setPath("/");
//		cookie2.setHttpOnly(true);
		cookie2.setSecure(false);//httpsを使用している場合true
		cookie.setDomain("localhost");
		cookie2.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
        response.addCookie(cookie);
        response.addCookie(cookie2);
        return "Cookieを保存しました";
	}
	
	@PostMapping("/bk/login")
	public boolean login(@RequestParam("id") String id,
						@RequestParam("password") String password,
						HttpServletResponse response) {
		User user = userRepository.loginCheck(id, password);
		boolean loginCheck = false;
		if(user != null) {
			loginCheck = true;
			String jsonUser = "";
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonUser = mapper.writeValueAsString(user);
				
				// JSON文字列をBase64エンコード
                String encodedJsonUser = Base64.getEncoder().encodeToString(jsonUser.getBytes());
                
                System.out.println(jsonUser);
                System.out.println(encodedJsonUser);
    			Cookie cookie = new Cookie("user", encodedJsonUser);
    			cookie.setPath("/");
    			cookie.setHttpOnly(true);
    			cookie.setSecure(false);//httpsを使用している場合true
    			cookie.setDomain("localhost");
    			cookie.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
    			response.addCookie(cookie);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
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
