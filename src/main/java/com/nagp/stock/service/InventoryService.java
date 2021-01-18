package com.nagp.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

//import com.google.gson.Gson;
import com.nagp.stock.dto.ProductStockDTO;
import com.nagp.stock.entity.Stock;
import com.nagp.stock.repo.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;
	
	public String getStockForProduct(String productCode){
		List<Stock> stocks = inventoryRepository.findByProductCode(productCode);
		if (!CollectionUtils.isEmpty(stocks)) {	
//			String json = new Gson().toJson(stocks.get(0));
			String json = stocks.get(0).toString();
			return json;
		}
		return null;
	}
	
	public String getAllStocks(){
		Iterable<Stock> iter = inventoryRepository.findAll();
		List<Stock> list = new ArrayList<Stock>();
		for (Stock stock : iter) {
	        list.add(stock);
	    }
//		String json = new Gson().toJson(list);
		String json = list.toString();
		return json;
	}
	
	public boolean reserveProductStock(ProductStockDTO productStockDTO){
		boolean flag = true;
		List<Stock> stocks = inventoryRepository.findByProductCode(productStockDTO.getProductCode());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {			
			stock = stocks.get(0);
		} if (null != stock) {
			stock.reserveStock(productStockDTO.getQuantity());
			inventoryRepository.save(stock);
		} else {
			flag = false;
		}
		return flag;
	}
	
	public boolean insertProductStock(String code, int availabile){
		boolean flag = false;
		if (code != null) {
			Stock stock = new Stock();
//			stock.setId(UUID.randomUUID());
			stock.setProductCode(code);
			stock.setAvailable(availabile);
			stock.setReserved(0);
			inventoryRepository.save(stock);
			flag = true;
		}
		return flag;
	}
	
	public boolean reduceProductStock(ProductStockDTO productStockDTO){
		boolean flag = true;
		List<Stock> stocks = inventoryRepository.findByProductCode(productStockDTO.getProductCode());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {			
			stock = stocks.get(0);
		} 
		if (null != stock) {
			stock.deduceStock(productStockDTO.getQuantity());
			inventoryRepository.save(stock);
		} else {
			flag = false;
		}
		return flag;
	}
	
	public boolean addProductStock(ProductStockDTO productStockDTO){
		boolean flag = true;
		List<Stock> stocks = inventoryRepository.findByProductCode(productStockDTO.getProductCode());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {			
			stock = stocks.get(0);
		}
		if (null != stock) {
			stock.addStock(productStockDTO.getQuantity());
			inventoryRepository.save(stock);
		} else {
			flag = false;
		}
		return flag;
	}
}
