package com.jindal.auction.service;

import java.util.List;
import java.util.Map;

import com.jindal.auction.domain.Consumption;
import com.jindal.auction.domain.CustomerServiceStatus;
import com.jindal.auction.domain.ProductConfigDetails;

public interface ConsumptionService {
	
	public String saveConsumptionDetails(Consumption consumption);
	
	public List<CustomerServiceStatus> getCustomerServiceStatus(Map<String, String> map);

	public Double getNoOfserviceAvailable(Consumption consumptionDetails);

	public String changeServiceStatus(Consumption consumption);

	public String inActiveServiceStatus();

	public List<ProductConfigDetails> getProductConfigList(String configIds);

}
