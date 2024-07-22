package com.example.demo.entity;

import com.example.demo.model.ViewUserModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_info")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //JSONでオブジェクトを返すために余計なやつをシリアライズしない
public class User {
	
	@Id
	@Column(name = "user_id")
	private String id;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "session")
	private String session;
	
	@Column(name = "product")
	private String products;
	
	public ViewUserModel getViewUserModel() {
		ViewUserModel user = new ViewUserModel();
		user.setId(id);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setMail(mail);
		user.setAddress(address);
		user.setTel(tel);
		return user;
	}
}
