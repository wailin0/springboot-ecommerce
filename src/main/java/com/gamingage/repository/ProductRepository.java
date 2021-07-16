package com.gamingage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategory(String category);





	Product findByNameContainingIgnoreCase(String name);

	List<Product> findAllByOrderByIdDesc();



	List<Product> findByCategoryContainingIgnoreCaseOrderByIdDesc(String category);



	List<Product> findByTypeContainingIgnoreCaseOrderByIdDesc(String type);



	List<Product> getTop5ByCategoryOrderByIdDesc(String string);



}
