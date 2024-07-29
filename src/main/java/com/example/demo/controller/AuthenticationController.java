package com.example.demo.controller;

import java.util.Base64;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.model.ConfirmationUtil;
import com.example.demo.model.ViewUserModel;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
            String session = ConfirmationUtil.codeGenerate(15);
            System.out.println(session);
            //sessionが被っているかどうか確認
            while(true) {
            	User isSession = userRepository.isSession(session);
                if(isSession == null) {
                	break;
                } else {
                	session = ConfirmationUtil.codeGenerate(15);
                }
            }
            userRepository.updateSession(session, user.getId());
            String encodedSession = Base64.getEncoder().encodeToString(session.getBytes());
            System.out.println(encodedSession);
			Cookie cookie = new Cookie("loginSession", encodedSession);
			cookie.setPath("/");
			cookie.setSecure(false);//httpsを使用している場合true
			cookie.setDomain("azurewebsites.net");
			cookie.setMaxAge(7 * 24 * 60 * 60); // 1週間の有効期限
			response.addCookie(cookie);
		}
		return loginCheck;
	}
	
	@GetMapping("/bk/getUserCookie")
	public String getUserValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("loginSession".equals(cookie.getName())) {
					String session = new String(Base64.getDecoder().decode(cookie.getValue()));
					System.out.println(session);
					User user = userRepository.isSession(session);
					if(user != null) {
						ViewUserModel viewUser = user.getViewUserModel();
						String jsonUser = "";
						ObjectMapper mapper = new ObjectMapper();
						try {
							jsonUser = mapper.writeValueAsString(viewUser);
							System.out.println(jsonUser);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						return jsonUser;
					}
				}
			}
		}
		return "";
	}
	
	@GetMapping("/bk/checkLogin")
	public boolean checkLogin(HttpServletRequest request) {
		boolean checkLogin = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("loginSession".equals(cookie.getName())) {
					String session = new String(Base64.getDecoder().decode(cookie.getValue()));
					System.out.println(session);
					User user = userRepository.isSession(session);
					if(user != null) {
						checkLogin = true;
					}
				}
			}
		}
		return checkLogin;
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
