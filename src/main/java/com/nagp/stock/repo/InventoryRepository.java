package com.nagp.stock.repo;

import org.springframework.stereotype.Repository;

import com.nagp.stock.entity.Stock;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

@Repository
public interface InventoryRepository extends JpaRepository<Stock, String>{

	public List<Stock> findByProductId(String productId);
}
