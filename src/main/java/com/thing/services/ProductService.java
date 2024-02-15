package com.thing.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thing.models.Product;
import com.thing.repositories.ProductRepository;

@Service
public class ProductService {
	ProductRepository productRepository;
	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
}
