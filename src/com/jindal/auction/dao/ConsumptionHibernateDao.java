package com.jindal.auction.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.jindal.auction.domain.Consumption;
import com.jindal.auction.domain.CustomerServiceStatus;
import com.jindal.auction.domain.ProductConfigDetails;
import com.jindal.auction.utils.HibernateUtil;


/*****************************************************************************************************************
 *      		
 * <p>This class is for followings purpose 
 * 
 * <ul>
 * <li> save Service Consumption Details
 * <li> get Customer Service Status Informations
 * <li> get No Of Available Services
 * <li> change Service Status to active or inactive
 * </ul>
 *
 * 
 * Copyright :    		
 * 
 * ****************************************************************************************************************/
public class ConsumptionHibernateDao implements ConsumptionDao {

	Session session;
	Transaction trans;
	
	/**
	 *<p>
	 * This method used for save consumption details of a customer.
	 * It will save update the consumed quantity and last consumption date if there are the customer have remaining quantity of a product.
	 * If have remaining quantity and update successfully then it will return String "saved" .
	 * If not have remaining quantity then it will return String "notEnoughPurchageQty" .
	 * Else it will return String "notSaved" if an error occurred
	 * 
	 * @param Consumption object that contains String customerId, String productCode, Double quantity, String uom, Date lastConsumptionDate
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String saveConsumptionDetails(Consumption consumption) {
		String status = "notSaved";
	
		try{
			session =HibernateUtil.getSession();
			trans = session.beginTransaction();
			
			//save data in service_consumtion_history table
			session.saveOrUpdate(consumption);
			
			//updating data in customer_service_status table
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date lastconsumptionDate=consumption.getConsumedDate();
			sd.format(lastconsumptionDate);
			
			
			//check condition that purchased quantity not grater that total used quantity
			//SQLQuery checkQuery=session.createSQLQuery("select quantity from customer_service_status where  customerId='"+consumption.getCustomerId()+"' and productCode='"+consumption.getProductCode()+"' ");
			int serviceId=Integer.parseInt(consumption.getProductCode());
			String remainingQty=session.createSQLQuery("select quantity-consumptionQuantity from customer_service_status  where customerId='"+consumption.getCustomerId()+"' and serviceId="+serviceId+"").uniqueResult().toString();
			
			System.out.println("remaining qty : "+remainingQty);
			
			Double remainingQuantity=Double.parseDouble(remainingQty);
			if(remainingQuantity-consumption.getConsumedQuantity()<0)
			{
				status="notEnoughPurchageQty";
			}
			else
			{
			SQLQuery query=session.createSQLQuery("update customer_service_status set consumptionQuantity=consumptionQuantity+'"+consumption.getConsumedQuantity()+"' , lastConsumptionDate='"+sd.format(lastconsumptionDate)+"' where customerId='"+consumption.getCustomerId()+"' and serviceId="+serviceId+" ");
			int i= query.executeUpdate();
			trans.commit();
			if(i>0)
			{
				status="saved";
			}
			else {
				status="notSaved";
			}
			
			}
		}catch (HibernateException e) {
			System.out.println("exception is"+e.getMessage());
			e.printStackTrace();		
			
		} finally {
			session.close();
		}
		return status;
	}
	
	
	
	
	/**
	 *<p>
	 * This method used for get Customer Service Status details of a customer according to given search conditions.
	 * 
	 * @param Consumption object that contains String customerId, String productCode, Double quantity, String uom, Date lastConsumptionDate
	 * 
	 * @return  List<CustomerServiceStatus>
	 * 
	 * @exception 
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public List<CustomerServiceStatus> getCustomerServiceStatus(Map<String, String> map)
	{
		List<CustomerServiceStatus> listCustomerServiceStatus=new ArrayList<CustomerServiceStatus>();
		Session session1 = null;
		try {
			
			String searchCondition=map.get("condition");
			
			if(!searchCondition.equals("")){
				searchCondition=" where"+searchCondition;
			}
	
			session1 = HibernateUtil.getSession();
		
			Query query = session1
					.createQuery("select serviceId as serviceId ,customerId as customerId, customerName as customerName, productCode as productCode, productDescription as productDescription, quantity as quantity, uom as uom, serviceStartDate as serviceStartDate, serviceEndDate as serviceEndDate, consumptionQuantity as consumptionQuantity, lastConsumptionDate as lastConsumptionDate,status as status, likeDislike as likeDislike, comment as comment, rating as rating, share as share, rfq as rfq, blog as blog, configId as configId, shipmentId as shipmentId, orderId as orderId  from CustomerServiceStatus  "+searchCondition)
					.setResultTransformer(
							Transformers
									.aliasToBean(CustomerServiceStatus.class));
			
			
			
			listCustomerServiceStatus = query.list();
			
			System.out.println("list is :::....." + listCustomerServiceStatus);
			
			List<ProductConfigDetails> prodConfigList=new ArrayList<ProductConfigDetails>();
			
			
			for(CustomerServiceStatus customerServiceStatus:listCustomerServiceStatus)
			{
				/*System.out.println(customerServiceStatus.getCustomerId());
				System.out.println(customerServiceStatus.getCustomerName());
				System.out.println(customerServiceStatus.getProductCode());
				System.out.println(customerServiceStatus.getProductDescription());
				System.out.println(customerServiceStatus.getUom());
				System.out.println(customerServiceStatus.getConsumptionQuantity());
				System.out.println(customerServiceStatus.getServiceStartDate());
				System.out.println(customerServiceStatus.getServiceEndDate());*/
				
				System.out.println("congigId="+customerServiceStatus.getConfigId());
				
				if(customerServiceStatus.getConfigId()!=null)
				{
				prodConfigList=getProductConfigList(customerServiceStatus.getConfigId());
				customerServiceStatus.setProductConfigList((ProductConfigDetails[]) prodConfigList.toArray(new ProductConfigDetails[prodConfigList.size()]));
				}
				
			}
		
			System.out.println("-----------------------"+listCustomerServiceStatus);
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} finally {
			session1.close();
			return listCustomerServiceStatus;
		}
		
		
	}
	
	
	
	
	/**
	 *<p>
	 * This method used to get No Of Available Service of a customer .
	 * 
	 * @param Consumption object that contains customerId and productCode 
	 * 
	 * @return  Double
	 * 
	 * @exception 
	 */
	public Double getNoOfserviceAvailable(Consumption consumptionDetails) {
		Double remainingQuantity=0.0;
		try
		{
			
			session =HibernateUtil.getSession();
		
			String remainingQty=session.createSQLQuery("select quantity-consumptionQuantity from customer_service_status  where customerId='"+consumptionDetails.getCustomerId()+"' and productCode='"+consumptionDetails.getProductCode()+"'").uniqueResult().toString();
			
			System.out.println("remaining qty : "+remainingQty);
			
			remainingQuantity=Double.parseDouble(remainingQty);
			
		
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally
		{
			session.close();
		}
		
		
		return remainingQuantity;
	}




	/**
	 *<p>
	 * This method used to change Service Status
	 * 
	 * @param Consumption object that contains customerId and productCode , status
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String changeServiceStatus(Consumption consumption) {
		
		
		String status = "notSaved";
		
		try{
			session =HibernateUtil.getSession();
			trans = session.beginTransaction();
			
			//save data in service_consumtion_history table
			session.saveOrUpdate(consumption);
			
			//updating data in customer_service_status table
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date lastconsumptionDate=new Date();
			sd.format(lastconsumptionDate);
			
			SQLQuery query=session.createSQLQuery("update customer_service_status set status='"+consumption.getStatus()+"'  where customerId='"+consumption.getCustomerId()+"' and productCode='"+consumption.getProductCode()+"' ");
			
			int i= query.executeUpdate();
			trans.commit();
			if(i>0)
			{
				status="saved";
			}
			else {
				status="notSaved";
			}
			
			
		}catch (HibernateException e) {
			System.out.println("exception is"+e.getMessage());
			e.printStackTrace();		
			
		} finally {
			session.close();
		}
		return status;
		
	}




	/**
	 *<p>
	 * This method used to inactive Service Status if product service date expire or not have remaining quantity.
	 * 
	 * @param 
	 * 
	 * @return  String
	 * 
	 * @exception 
	 */
	public String inActiveServiceStatus() {
		
		String status = "notSaved";
		
		try{
			session =HibernateUtil.getSession();
			trans = session.beginTransaction();
		
			//updating data in customer_service_status table
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate=new Date();
			sd.format(currentDate);
		
			
			SQLQuery query=session.createSQLQuery("update customer_service_status set status='inactive'  where now() >= service_end_date OR (quantity-consumptionQuantity)<=0 ");
			int i= query.executeUpdate();
			trans.commit();
			if(i>0)
			{
				status="saved";
			}
			else {
				status="notSaved";
			}
			
			
		}catch (HibernateException e) {
			System.out.println("exception is"+e.getMessage());
			e.printStackTrace();		
			
		} finally {
			session.close();
		}
		return status;
	}



	/**
	 *<p>
	 * This method used to get ProductConfig List. 
	 * 
	 * @param configIds
	 * 
	 * @return  List<ProductConfigDetails>
	 * 
	 * @exception 
	 */
	@SuppressWarnings("unchecked")
	public List<ProductConfigDetails> getProductConfigList(String configIds) {
	
		List<ProductConfigDetails> productConfigList=new ArrayList<ProductConfigDetails>();
		
		try {
				session = HibernateUtil.getSession();
	
				Query query1 = session
					.createQuery("select configId as configId, configItemId as configItemId, SeqNo as SeqNo, configOptionId as configOptionId, productId as productId, quantity as quantity, productName as productName, uomId as uomId, uomName as uomName, categoryName as categoryName from ProductConfigDetails where configId in ("+configIds+") ")
					.setResultTransformer(
							Transformers
									.aliasToBean(ProductConfigDetails.class));
				
				productConfigList=query1.list();
			
			System.out.println("list is :::....." + productConfigList);
			
			for(ProductConfigDetails productConfigDetails:productConfigList)
			{
				System.out.println("config id="+productConfigDetails.getConfigId());
				System.out.println("prod Id="+productConfigDetails.getProductId());
				System.out.println("prod name="+productConfigDetails.getProductName());
				System.out.println("qty="+productConfigDetails.getQuantity());
				System.out.println("uom name="+productConfigDetails.getUomName());
		
			}
		
			
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return productConfigList;
		
		
		
	}
	
	/*public static void main(String[] ar)
	{
		System.out.println("main is calling..");
		ConsumptionHibernateDao consumptionHibernateDao =  new ConsumptionHibernateDao();
		//consumptionHibernateDao.getCustomerServiceStatus("1001");
		
		List<String>  list=new ArrayList<String>();
		list.add("10000");
		list.add("11000");
		
		consumptionHibernateDao.getProductConfigList("10000,11000");
	}*/


	
	
}
	



