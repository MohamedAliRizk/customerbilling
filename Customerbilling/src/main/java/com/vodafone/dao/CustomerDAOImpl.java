package com.vodafone.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vodafone.model.Customer;

@Component
public class CustomerDAOImpl implements CustomerDAO {

//	public static List<Customer> customers;
//	public CustomerDAOImpl(){
//		customers = new ArrayList<>();
//		Customer.FullName fullName1 = new Customer().new FullName("Bothinah" , "Mostafa" , "Youssef");
//		Customer.FullName fullName2 = new Customer().new FullName("Dina" , "Ashraf" , "Youssef");
//		Customer.FullName fullName3 = new Customer().new FullName("Hatem" , "Mostafa" , "Youssef");
//		Customer.FullName fullName4 = new Customer().new FullName("Tallat" , "Mostafa" , "Youssef");
//		customers.add(new Customer(fullName1 , 1l));
//		customers.add(new Customer(fullName2 , 2l));
//		customers.add(new Customer(fullName3 , 3l));
//		customers.add(new Customer(fullName4 , 4l));
//	}
	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
