package com.nagp.stock.service;

import java.util.ArrayList;
import java.util.List;

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

	public ProductStockDTO getStockForProduct(String productId) {
		List<Stock> stocks = inventoryRepository.findByProductId(productId);
		if (!CollectionUtils.isEmpty(stocks)) {
			return stockToProductStockDTO(stocks.get(0));
		}
		return null;
	}

	public List<ProductStockDTO> getAllStocks() {
		Iterable<Stock> iter = inventoryRepository.findAll();
		List<ProductStockDTO> list = new ArrayList<>();
		for (Stock stock : iter) {
			list.add(stockToProductStockDTO(stock));
		}
		return list;
	}

	public boolean reserveProductStock(ProductStockDTO productStockDTO) {
		boolean flag = true;
		List<Stock> stocks = inventoryRepository.findByProductId(productStockDTO.getProductId());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {
			stock = stocks.get(0);
		}
		if (null != stock) {
			if (stock.reserveStock(productStockDTO.getQuantity()))
				inventoryRepository.save(stock);
			else
				flag = false;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean insertProductStock(String code, int availabile) {

		boolean flag = false;

		if (code != null) {
			Stock stock = new Stock();
			stock.setProductId(code);
			stock.setAvailable(availabile);
			stock.setReserved(0);
			inventoryRepository.save(stock);
			flag = true;
		}
		return flag;
	}

	public boolean commitProductStock(ProductStockDTO productStockDTO) {
		boolean flag = true;
		List<Stock> stocks = inventoryRepository.findByProductId(productStockDTO.getProductId());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {
			stock = stocks.get(0);
		}
		if (null != stock) {
			if (stock.commitStock(productStockDTO.getQuantity()))
				inventoryRepository.save(stock);
			else
				flag = false;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean addProductStock(ProductStockDTO productStockDTO) {
		List<Stock> stocks = inventoryRepository.findByProductId(productStockDTO.getProductId());
		Stock stock = null;
		if (!CollectionUtils.isEmpty(stocks)) {
			stock = stocks.get(0);
			stock.setAvailable(stock.getAvailable() + productStockDTO.getQuantity());
			inventoryRepository.save(stock);
		} else {
			return insertProductStock(productStockDTO.getProductId(), productStockDTO.getQuantity());
		}
		return true;
	}

	private ProductStockDTO stockToProductStockDTO(Stock stock) {
		ProductStockDTO stockDTO = new ProductStockDTO();
		stockDTO.setProductId(stock.getProductId());
		stockDTO.setQuantity((int) stock.getAvailable());
		stockDTO.setReserve((int) stock.getReserved());
		return stockDTO;
	}
}
