package com.example.demo.controller;

import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
public class RevisionController {
	private final UserRepository userRepository;
	
	@PostMapping("/basicRevision")
	public String basicRevision(@RequestParam("id") String id,
							    @RequestParam("lastName") String lastName,
							    @RequestParam("firstName") String firstName,
							    @RequestParam("address") String address,
							    @RequestParam("tel") String tel) {
		userRepository.updateBasicData(lastName, firstName, address, tel, id);
		String message = "更新しました。";
		return message;
	}
	
	@PostMapping("/mailRevision")
	public String basicRevision(@RequestParam("id") String id,
							    @RequestParam("mail") String mail) {
		userRepository.updateMailData(mail, id);
		String message = "更新しました。";
		return message;
	}
	
	@PostMapping("/idRevision")
	public String idRevision(@RequestParam("currentId") String currentId,
						     @RequestParam("id") String id) {
		userRepository.updateIdData(currentId, id);
		System.out.println(id);
		String message = "更新しました。";
		return message;
	}
	
	@PostMapping("/matchPassword")
	public String matchPassword(@RequestParam("id") String id,
								@RequestParam("oldPassword") String password) {
		User user = userRepository.loginCheck(id, password);
		String message = "";
		if(Objects.nonNull(user)) {
			message = "一致";
		}
		
		return message;
	}
	
	@PostMapping("/passwordRevision")
	public String passwordRevision(@RequestParam("id") String id,
								   @RequestParam("newPassword") String password) {
		userRepository.updatePassword(id, password);
		String message = "更新しました。";
		return message;
	}

}
