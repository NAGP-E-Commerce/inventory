package com.nagp.stock.entity;

//import org.springframework.data.annotation.Id;
import javax.persistence.Id;

import lombok.Data;
import javax.persistence.Entity;
//import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity(name = "stock")
@Data
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String productCode;

	private long available;

	private long reserved;
	
	public boolean addStock(int quantity) {
		available += quantity;
		return true;
	}
	
	public boolean deduceStock(int quantity) {
		boolean isAvailable = isAvailable(quantity);
		if (isAvailable) {
			available -= quantity;
		}
		return isAvailable;
	}
	
	public boolean reserveStock(int quantity) {
		boolean isAvailable = isAvailable(quantity);
		if (isAvailable) {
			reserved += quantity;
		}
		return isAvailable;
	}
	
	private boolean isAvailable(int quantity) {
		return available - quantity >= 0 ? true : false;
	}
}
