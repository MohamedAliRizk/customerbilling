package com.vodafone.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vodafone.model.Bill;
import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;

@Repository
public class BillDAOImpl implements BillDAO {

	public static List<Bill> bills;
	Bill bill1, bill2;
	@Autowired
	CustomerService customerService;

	@Override
	public List<Bill> findAll(Customer customer) {
		// TODO Auto-generated method stub

		bills = new ArrayList<>();

		bill1 = new Bill();
		bill1.setCustomer(customer);
		bill1.setId(1l);
		bill1.setPaid(1000);
		bill1.setDueDate(new Date());

		bill2 = new Bill(2, customer, 2000, new Date());

		bills.add(bill1);
		bills.add(bill2);
		return bills;

	}

}
