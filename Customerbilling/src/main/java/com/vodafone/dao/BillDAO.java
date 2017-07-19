package com.vodafone.dao;

import java.util.List;

import com.vodafone.model.Bill;
import com.vodafone.model.Customer;


public interface BillDAO {

	List<Bill> findAll(Customer customer);
}
