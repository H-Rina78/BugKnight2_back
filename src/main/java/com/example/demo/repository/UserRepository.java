package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "SELECT * FROM user_info "
			+ "WHERE crypt(:password, password) = password "
			+ "AND user_id = :id", nativeQuery = true)
	User loginCheck(@Param("id") String id,
					@Param("password") String password);
	
	@Query(value = "SELECT * FROM user_info "
			     + "WHERE user_id = :id ", nativeQuery = true)
	User isId(@Param("id") String id);
	
	@Query(value = "SELECT * FROM user_info "
		     + "WHERE mail = :mail", nativeQuery = true)
	User isMail(@Param("mail") String mail);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO user_info(user_id, last_name, first_name, mail, password, address, tel) "
			     + "VALUES(:id, :lastName, :firstName, :mail, crypt(:password, gen_salt('bf')), :address, :tel)", nativeQuery = true)
	void insertUser(@Param("id") String id,
			        @Param("lastName") String lastName,
			        @Param("firstName") String firstName,
			        @Param("mail") String mail,
			        @Param("password") String password,
			        @Param("address") String address,
			        @Param("tel") String tel);
	
}
