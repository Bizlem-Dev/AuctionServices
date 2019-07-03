package com.jindal.auction.auctionServies;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jindal.auction.domain.Consumption;
import com.jindal.auction.domain.CustomerServiceStatus;
import com.jindal.auction.domain.ProductConfigDetails;
import com.jindal.auction.service.ConsumptionService;
import com.jindal.auction.service.ConsumptionServiceImpl;

/*****************************************************************************************************************
 *      		
 * <p>This service class is used to create webServices  for : 
 * 
 * <ul>
 * <li> Service Consumption
 * <li> get Customer Service Status Informations
 * <li> get No Of Available Services
 * <li> change Service Status to active or inactive
 * </ul>
 *
 * 
 * Copyright :    		
 * 
 * ****************************************************************************************************************/

public class Auctions_WSDL {

	
	ConsumptionService consumptionService = new ConsumptionServiceImpl();
	
	/**
	 *<p>
	 * This method used for save consumption details of a customer.
	 * 
	 * @param  String customerId, String productCode, Double quantity, String uom, Date lastConsumptionDate
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String serviceConsumption(String customerId, String productCode,
			Double quantity, String uom, Date lastConsumptionDate) {
		try {
			
			Consumption consumptionDetails = new Consumption();
			
			System.out.println("Calling serviceConsumption");
			
			System.out.println(customerId);
			System.out.println(productCode);
			System.out.println(quantity);
			System.out.println(uom);
			System.out.println(lastConsumptionDate);
		
			//saving consumed details in data bases ....
			consumptionDetails.setCustomerId(customerId);
			consumptionDetails.setProductCode(productCode);
			consumptionDetails.setConsumedQuantity(quantity);
			consumptionDetails.setUom(uom);
			consumptionDetails.setConsumedDate(lastConsumptionDate);
			
			ConsumptionService consumptionService =  new ConsumptionServiceImpl();
			String b=consumptionService.saveConsumptionDetails(consumptionDetails);
			
			return b;

		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
			
			return ex.getMessage();
			
		}

	}
	



	/**
	 *<p>
	 * This method used to get Customer Service Status of customer according to given search conditions. 
	 * To use this service you have to provide 1 or more parameters.
	 * 
	 * @param  String customerId,String productId, String fromDate, String toDate, String status
	 * 
	 * @return  List<CustomerServiceStatus>
	 * 
	 * @exception 
	 */
	public List<CustomerServiceStatus> getCustomerServiceStatus(String customerId,String productId, String fromDate, String toDate, String status)
	{
		List<CustomerServiceStatus> customerServiceList= new ArrayList<CustomerServiceStatus>();
		
		Map<String, String> map=new HashMap<String, String>();
		try
		{
			String custoId="";
			String prodId="";
			String frmDate="";
			String tDate="";
			String sts="";
			
			if(customerId!=null)
			{
				custoId=customerId;
			}
			if(productId!=null)
			{
				prodId=productId;
			}
			if(fromDate!=null)
			{
				frmDate=fromDate;
			}
			if(toDate!=null)
			{
				tDate=toDate;
			}
			if(status!=null)
			{
				sts=status;
			}
			
				map.put("customerId", custoId);
				map.put("productId", prodId);
				map.put("fromDate", frmDate);
				map.put("toDate", tDate);
				map.put("status", sts);
				
			
			
			customerServiceList= consumptionService.getCustomerServiceStatus(map);
			
			//System.out.println("date is =================================================="+customerServiceList.get(0).getLastConsumptionDate());
			
			System.out.println("customerServiceList===>>>"+customerServiceList);
			
		}
		catch (Exception e) {
			
		}
		
		return customerServiceList;
	}
	
	
	
	
	
	
	/**
	 *<p>
	 * This method used to get No Of service Available of a Customer. 
	 * All parameters are mandatory to use this service.
	 * 
	 * @param  String customerId, String productCode
	 * 
	 * @return  Double
	 * 
	 * @exception 
	 */
	public Double getNoOfserviceAvailable(String customerId, String productCode) {
		Double noOfService=0.0;
		try {
			
			Consumption consumptionDetails = new Consumption();
			
			System.out.println("Calling noOfserviceAvailable");
			
			System.out.println(customerId);
			System.out.println(productCode);
		
			consumptionDetails.setCustomerId(customerId);
			consumptionDetails.setProductCode(productCode);
		
			ConsumptionService consumptionService =  new ConsumptionServiceImpl();
			noOfService=consumptionService.getNoOfserviceAvailable(consumptionDetails);
	

		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
			

		}

		return noOfService;
		
	}
	
	
	
	
	
	
	/**
	 *<p>
	 * This method used to change Service Status to active or inactive. 
	 * All parameters are mandatory to use this service. 
	 * 
	 * @param  String customerId, String productCode, String status
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String changeServiceStatus(String customerId, String productCode, String status) {
		try {
			
			Consumption consumptionDetails = new Consumption();
			
			System.out.println("Calling serviceConsumption");
			
			System.out.println(customerId);
			System.out.println(productCode);
			System.out.println(status);
			
			
			
			//saving consumed details in data bases ....
			consumptionDetails.setCustomerId(customerId);
			consumptionDetails.setProductCode(productCode);
			consumptionDetails.setStatus(status);
		
			
			ConsumptionService consumptionService =  new ConsumptionServiceImpl();
			String b=consumptionService.changeServiceStatus(consumptionDetails);
			
			return b;

		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
			
			return ex.getMessage();
			
		}

	}
	
	
	
	
	
	/*public List<ProductConfigDetails> getProductConfigList(String configIds)
	{
		List<ProductConfigDetails> productConfigList= new ArrayList<ProductConfigDetails>();
		
		if(configIds==null)
		{
			configIds="";
		}
		
		try
		{
			if(!configIds.equals(""))
			{
			productConfigList= consumptionService.getProductConfigList(configIds);
			}
			
		}
		catch (Exception e) {
			
		}
		
		return productConfigList;
	}*/
	
	
	
	public static void main(String ar[])
	{
		
		Auctions_WSDL auctions_WSDL=new Auctions_WSDL();
		
		System.out.println("in main list is ="+auctions_WSDL.getCustomerServiceStatus("","" , "", "", ""));
	}
	
	
	
}
