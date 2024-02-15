package com.thing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thing.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
