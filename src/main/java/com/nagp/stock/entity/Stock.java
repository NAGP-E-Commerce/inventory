package com.nagp.stock.entity;

import javax.persistence.Entity;
//import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import org.springframework.data.annotation.Id;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "stock")
@Data
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String productId;

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
		boolean res = available - reserved - quantity >= 0 ? true : false;
		return res;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	public long getReserved() {
		return reserved;
	}

	public void setReserved(long reserved) {
		this.reserved = reserved;
	}
}
