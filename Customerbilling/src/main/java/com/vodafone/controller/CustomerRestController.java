package com.vodafone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.dao.CustomerDAO;
import com.vodafone.model.Customer;

@RequestMapping("/Customers")
@RestController
@Secured("ROLE_ADMIN")

public class CustomerRestController {
	CustomerDAO customerDAO;
	
	@Autowired
	public CustomerRestController(CustomerDAO customerDAO){
		this.customerDAO = customerDAO;
	}
	
	@RequestMapping(method=RequestMethod.GET , produces="application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Customer> getAllCustomers(){
		System.out.println("Inside getAllCustomers.");
		return customerDAO.findAll();
	}

}
