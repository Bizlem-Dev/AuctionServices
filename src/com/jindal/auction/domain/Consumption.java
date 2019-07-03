package com.jindal.auction.domain;

import java.util.Date;

public class Consumption {
	
	private int consumptionId;
	private String customerId;
	private String productCode;
	private Double consumedQuantity;
	private String uom;
	private Date consumedDate;
	
	private String status;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Double getConsumedQuantity() {
		return consumedQuantity;
	}
	public void setConsumedQuantity(Double consumedQuantity) {
		this.consumedQuantity = consumedQuantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public int getConsumptionId() {
		return consumptionId;
	}
	public void setConsumptionId(int consumptionId) {
		this.consumptionId = consumptionId;
	}
	public Date getConsumedDate() {
		return consumedDate;
	}
	public void setConsumedDate(Date consumedDate) {
		this.consumedDate = consumedDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
