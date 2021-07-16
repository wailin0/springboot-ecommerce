package com.gamingage.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingage.model.Product;
import com.gamingage.repository.ProductRepository;


@Service
public class ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll(){
		return (List<Product>)productRepository.findAll();
	}

	public Product findOne(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public Product findByNameContainingIgnoreCase(String productName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}