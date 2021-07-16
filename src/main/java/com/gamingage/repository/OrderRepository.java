package com.gamingage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByStatus(String status);

	List<Order> findByUserId(Long id);

}
