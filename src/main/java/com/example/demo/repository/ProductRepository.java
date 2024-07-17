package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	//上限と下限のpriceでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "price BETWEEN :lowerPrice AND :upperPrice", nativeQuery = true)
	List<Product> findAllByPriceRenge(@Param("upperPrice") Integer upperPrice,
										@Param("lowerPrice") Integer lowerPrice);
	
	//カテゴリIdでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "category_id = :categoryId", nativeQuery = true)
	List<Product> findAllByCategoryId(@Param("categoryId") String categoryId);
	
	//カテゴリIdとpriceでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "category_id = :categoryId "
			+ "AND price BETWEEN :lowerPrice AND :upperPrice", nativeQuery = true)
	List<Product> findAllByCategoryIdByPriceRenge(@Param("categoryId") String categoryId,
													@Param("upperPrice") Integer upperPrice,
													@Param("lowerPrice") Integer lowerPrice);
	
	//キーワードでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "product_name LIKE '%'||:keyword||'%' "
			+ "OR product_hiragana LIKE '%'||:keyword||'%' "
			+ "OR product_katakana LIKE '%'||:keyword||'%'", nativeQuery = true)
	List<Product> findAllByKeyword(@Param("keyword") String keyword);
	
	//キーワードとpriceでセレクトする
	@Query(value = "SELECT * FROM product WHERE "
			+ "(product_name LIKE '%'||:keyword||'%' "
			+ "OR product_hiragana LIKE '%'||:keyword||'%' "
			+ "OR product_katakana LIKE '%'||:keyword||'%')"
			+ "AND price BETWEEN :lowerPrice AND :upperPrice", nativeQuery = true)
	List<Product> findAllByKeywordByPriceRenge(@Param("keyword") String keyword,
												@Param("upperPrice") Integer upperPrice,
												@Param("lowerPrice") Integer lowerPrice);
	
	// おすすめ商品を取得
		@Query(value = "SELECT * FROM product "
				     + "WHERE recommend_flg = '1'", nativeQuery = true)
		List<Product> findAllByRecommendFlg();
}
