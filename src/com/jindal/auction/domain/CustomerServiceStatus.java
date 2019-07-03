package com.jindal.auction.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerServiceStatus {
	
	private String customerId;
	private String customerName;
	private String productCode;
	private String productDescription;
	private Double quantity;
	private String uom;
	private Date serviceStartDate;
	private Date serviceEndDate;
	private Double consumptionQuantity;
	private Date lastConsumptionDate;
	
	private String likeDislike;
	private String comment;
	private String rating;
	private String share;
	private String rfq;
	private String blog;
	
	private String status;
	
	private String configId;
	
	private String shipmentId;
	private String orderId;
	private int serviceId;
	
	
	ProductConfigDetails[] productConfigList;
	
	

	
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	
	
	public Double getConsumptionQuantity() {
		return consumptionQuantity;
	}
	public void setConsumptionQuantity(Double consumptionQuantity) {
		this.consumptionQuantity = consumptionQuantity;
	}
	public Date getLastConsumptionDate() {
		return lastConsumptionDate;
	}
	public void setLastConsumptionDate(Date lastConsumptionDate) {
		this.lastConsumptionDate = lastConsumptionDate;
	}
	public String getLikeDislike() {
		return likeDislike;
	}
	public void setLikeDislike(String likeDislike) {
		this.likeDislike = likeDislike;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getRfq() {
		return rfq;
	}
	public void setRfq(String rfq) {
		this.rfq = rfq;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Date getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public ProductConfigDetails[] getProductConfigList() {
		return productConfigList;
	}
	public void setProductConfigList(ProductConfigDetails[] productConfigList) {
		this.productConfigList = productConfigList;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	

}
