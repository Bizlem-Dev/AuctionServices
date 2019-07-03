package com.jindal.auction.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.jindal.auction.dao.ConsumptionDao;
import com.jindal.auction.dao.ConsumptionHibernateDao;
import com.jindal.auction.domain.Consumption;
import com.jindal.auction.domain.CustomerServiceStatus;
import com.jindal.auction.domain.ProductConfigDetails;


/*****************************************************************************************************************
 *      		
 * <p>This serviceImpl class is used to call methods of  ConsumptionHibernateDao class
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

public class ConsumptionServiceImpl implements ConsumptionService {

	
	ConsumptionDao consumptionDao = new ConsumptionHibernateDao();
	
	/**
	 *<p>
	 * This method used to call saveConsumptionDetails(consumption) method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  Consumption class that contains customerId, productCode, quantity,uom, lastConsumptionDate
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String saveConsumptionDetails(Consumption consumption) {
		
		return consumptionDao.saveConsumptionDetails(consumption);
		
	}
	
	
	/**
	 *<p>
	 * This method used to call getCustomerServiceStatus(map) method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  Map<String, String>
	 * 
	 * @return  List<CustomerServiceStatus>
	 * 
	 * @exception 
	 */
	public List<CustomerServiceStatus> getCustomerServiceStatus(Map<String, String> map) {
		
		List<CustomerServiceStatus> list=new ArrayList<CustomerServiceStatus>();
		
			String condition="";
			String and="";
			
			try
			{
			if(!(map.get("customerId").equals("")))
			{
				//condition=" customerId ='"+map.get("customerId")+"' ";
				condition=" customerId Like '%"+map.get("customerId")+"%' ";
				and="and";
			}
			
			if(!(map.get("productId").equals("")))
			{
				//condition=condition+and+" productCode ='"+map.get("productId")+"' ";
				condition=condition+and+" productCode Like '%"+map.get("productId")+"%' ";
				and="and";
			}
			
			if(!(map.get("fromDate").equals("")) && (map.get("toDate").equals("")))
			{
				
				/*////
				String str_date=map.get("fromDate");
				DateFormat formatter ; 
				Date date ; 
				   formatter = new SimpleDateFormat("yyyy-MM-dd");
				   date = (Date)formatter.parse(str_date);
				   
				   System.out.println("**************"+date);

				////
*/			
				condition=condition+and+" DATE(serviceStartDate) ='"+map.get("fromDate")+"' ";
				and="and";
			}
			
			if(!(map.get("toDate").equals("")) && (map.get("fromDate").equals("")))
			{
				condition=condition+and+" DATE(serviceEndDate) ='"+map.get("toDate")+"' ";
				and="and";
			}
			
			if(!(map.get("toDate").equals("")) && !(map.get("fromDate").equals("")))
			{
				condition=condition+and+" DATE(serviceStartDate) >='"+map.get("fromDate")+"' and DATE(serviceEndDate) <='"+map.get("toDate")+"'  ";
				and="and";
			}
			
			if(!(map.get("status").equals("")))
			{
				if(!map.get("status").equals("all"))
				{
					condition=condition+and+" status ='"+map.get("status")+"' ";
				}
					
			}
			
			map.put("condition", condition);
			
			list=consumptionDao.getCustomerServiceStatus(map);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	

	/**
	 *<p>
	 * This method used to call getNoOfserviceAvailable(consumption) method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  Consumption class that contains customerId and productCode 
	 * 
	 * @return  Double
	 * 
	 * @exception 
	 */
	public Double getNoOfserviceAvailable(Consumption consumptionDetails) {
		
		return consumptionDao.getNoOfserviceAvailable(consumptionDetails);
	}

	/**
	 *<p>
	 * This method used to call getNoOfserviceAvailable(consumption) method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  Consumption class that contains customerId, productCode, status
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String changeServiceStatus(Consumption consumption) {
		
		return consumptionDao.changeServiceStatus(consumption);
	}

	
	/**
	 *<p>
	 * This method used to call inActiveServiceStatus() method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String inActiveServiceStatus() {
		
		return consumptionDao.inActiveServiceStatus();
		
	}

	/**
	 *<p>
	 * This method used to call getProductConfigList(configIds) method of ConsumptionHibernateDao class. 
	 *
	 * 
	 * @param  String configIds
	 * 
	 * @return  List<ProductConfigDetails>
	 * 
	 * @exception 
	 */
	public List<ProductConfigDetails> getProductConfigList(String configIds) {
		
		return consumptionDao.getProductConfigList(configIds);
	}

}
