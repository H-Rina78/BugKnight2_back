package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "SELECT * FROM user_info "
			+ "WHERE crypt(:password, password) = password "
			+ "AND user_id = :id", nativeQuery = true)
	User loginCheck(@Param("id") String id,
					@Param("password") String password);
	
	@Query(value = "SELECT * FROM user_info "
			     + "WHERE user_id = :id "
			     + "OR mail = :mail", nativeQuery = true)
	List<User> isUser(@Param("id") String id,
			    @Param("mail") String mail);
}
