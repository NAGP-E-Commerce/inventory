package com.nagp.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.stock.dto.ProductStockDTO;
import com.nagp.stock.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "InventoryControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("api/ecommerce")
@Validated
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping(path = "/inventory/getStock/{productCode}", method = RequestMethod.GET)
	@ApiOperation("Get stock for product")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = String.class)})
	public String getStockForProduct(@PathVariable(value = "productCode") String productCode){
		return inventoryService.getStockForProduct(productCode);
	}
	
	@RequestMapping(path = "/inventory/getAllStock", method = RequestMethod.GET)
	@ApiOperation("Get all product stock")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = String.class)})
	public String getStockForProduct(){
		return inventoryService.getAllStocks();
	}
	
	@RequestMapping(path = "/inventory/reserveStock", method = RequestMethod.POST)
	@ApiOperation("Reserve product stock")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Boolean.class)})
	public boolean reserveProductStock(@RequestBody ProductStockDTO productStockDTO){
		return inventoryService.reserveProductStock(productStockDTO);
	}
	
	@RequestMapping(path = "/inventory/insertStock", method = RequestMethod.POST)
	@ApiOperation("Insert in product stock")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Boolean.class)})
	public boolean insertProductStock(@RequestParam String code, @RequestParam int availabile){
		return inventoryService.insertProductStock(code, availabile);
	}
	
	@RequestMapping(path = "/inventory/reduceStock", method = RequestMethod.POST)
	@ApiOperation("Reduce product stock")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Boolean.class)})
	public boolean reduceProductStock(@RequestBody ProductStockDTO productStockDTO){
		return inventoryService.reduceProductStock(productStockDTO);
	}
	
	@RequestMapping(path = "/inventory/addStock", method = RequestMethod.POST)
	@ApiOperation("Add product stock")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Boolean.class)})
	public boolean addProductStock(@RequestBody ProductStockDTO productStockDTO){
		return inventoryService.addProductStock(productStockDTO);
	}
}
