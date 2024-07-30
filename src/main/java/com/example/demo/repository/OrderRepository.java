package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.OrderHistory;

public interface OrderRepository extends JpaRepository<OrderHistory, String> {
	@Query(value = "SELECT * FROM order_history "
			     + "WHERE user_id = :userId "
		         + "ORDER BY order_date "
		         + "limit 3", nativeQuery = true)
	List<OrderHistory> getAllOrderByUserId(@Param("userId") String userId);
	
	@Query(value = "select product from user_info "
				 + "where user_id = :id", nativeQuery = true)
	String getCartProduct(@Param("id") String userId);
	
	@Query(value = "insert into order_history(user_id, product_list, order_date) "
				 + "values(:userId, :productList, current_date)", nativeQuery = true)
	void insertOrder(@Param("userId") String userId,
					 @Param("productList") String productList);
	
	
}
