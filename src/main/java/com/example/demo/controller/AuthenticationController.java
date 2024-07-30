package com.example.demo.controller;

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
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AuthenticationController {
	private final UserRepository userRepository;

	@PostMapping("/bk/login")
	public String login(@RequestParam("id") String id,
			@RequestParam("password") String password,
			HttpServletResponse response,
			HttpServletRequest request) {
		User user = userRepository.loginCheck(id, password);
		String message = "false";
		if (user != null) {
			HttpSession session = request.getSession(true);
			String sessionId = session.getId();
			userRepository.updateSession(sessionId, user.getId());
			String encodedSessionId = ConfirmationUtil.encodeSessionId(sessionId);
			message = encodedSessionId;
		}
		return message;
	}

	@GetMapping("/bk/getUserCookie")
	public String getUserValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("loginSession".equals(cookie.getName())) {
					String session = ConfirmationUtil.decodeSessionId(cookie.getValue());
					System.out.println("session" + session);
					User user = userRepository.isSession(session);
					if (user != null) {
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
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("loginSession".equals(cookie.getName())) {
					String session = ConfirmationUtil.decodeSessionId(cookie.getValue());
					System.out.println(session);
					User user = userRepository.isSession(session);
					if (user != null) {
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
		if (Objects.nonNull(userId)) {
			message = "※このユーザIDは既に使用されています。";
		}

		if (Objects.nonNull(userMail)) {
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
