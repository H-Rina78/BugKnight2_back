package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	//カテゴリIdでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "category_id = :categoryId", nativeQuery = true)
	List<Product> findAllByCategoryId(@Param("categoryId") String categoryId);
	
	//上限と下限のpriceでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "price BETWEEN :lowerPrice AND :upperPrice", nativeQuery = true)
	List<Product> findAllByPriceRenge(@Param("upperPrice") Integer upperPrice,
										@Param("lowerPrice") Integer lowerPrice);
	
	//キーワードでセレクトする
		@Query(value = "SELECT * FROM product WHERE "
				+ "price = 1", nativeQuery = true)
		List<Product> findAllByKeyword(@Param("keyword") String keyword);
}
