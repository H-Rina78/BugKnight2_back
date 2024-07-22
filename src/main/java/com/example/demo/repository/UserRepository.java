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
	
	@Query(value = "SELECT * FROM user_info "
		     + "WHERE session = :session", nativeQuery = true)
	User isSession(@Param("session") String session);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_info SET session = :session WHERE user_id = :userId", nativeQuery = true)
	void updateSession(@Param("session") String session,
						@Param("userId") String userId);
	
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
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_info "
				 + "SET last_name = :lastName, first_name = :firstName, "
				 + "address = :address, tel = :tel "
				 + "WHERE user_id = :userId", nativeQuery = true)
	void updateBasicData(@Param("lastName") String lastName,
						 @Param("firstName") String firstName,
						 @Param("address") String address,
						 @Param("tel") String tel,
						 @Param("userId") String userId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_info "
				 + "SET mail = :mail "
				 + "WHERE user_id = :userId", nativeQuery = true)
	void updateMailData(@Param("mail") String mail,
						@Param("userId") String userId);
	
}
