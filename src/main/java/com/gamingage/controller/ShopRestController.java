package com.gamingage.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gamingage.model.Product;
import com.gamingage.repository.ProductRepository;

@RestController
public class ShopRestController {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	//for shop page
	//for getting all products
	@GetMapping("/rest/products")
	public List<Product> getAllProducts() {
		return productRepo.findAllByOrderByIdDesc();
	}
	
	//for shop page
	//for sorting products by category
	@GetMapping("/rest/products/category/{category}")
	public List<Product> findByCategory(@PathVariable String category) {
		return productRepo.findByCategoryContainingIgnoreCaseOrderByIdDesc(category);
	}
	
	
	//for sorting products by type(sub category)
	@GetMapping("/rest/products/type/{type}")
	public List<Product> findByType(@PathVariable String type) {
		return productRepo.findByTypeContainingIgnoreCaseOrderByIdDesc(type);
	}
	
	
	//for main page
	@GetMapping("/rest/newArrival/{category}")
	public List<Product> newArraival(@PathVariable String category){
		return productRepo.getTop5ByCategoryOrderByIdDesc(category);
	}
	
	

}
