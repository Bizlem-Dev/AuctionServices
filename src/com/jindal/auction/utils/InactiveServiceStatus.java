package com.jindal.auction.utils;

import com.jindal.auction.service.ConsumptionService;
import com.jindal.auction.service.ConsumptionServiceImpl;

public class InactiveServiceStatus {
	
	ConsumptionService consumptionService = new ConsumptionServiceImpl();
	
	
	public String inActiveServiceStatus()
	{
		String result = consumptionService.inActiveServiceStatus();
		
		return result;
	}
	
	
	
	public static void main(String[] ar)
	{	
		System.out.println("calling....");
		InactiveServiceStatus inactiveServiceStatus = new InactiveServiceStatus();
		
		String result = inactiveServiceStatus.inActiveServiceStatus();

		System.out.println("result is ..."+result);
	}

}
