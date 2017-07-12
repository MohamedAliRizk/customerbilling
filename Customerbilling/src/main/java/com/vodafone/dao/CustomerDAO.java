package com.vodafone.dao;

import java.util.List;

import com.vodafone.model.Customer;

public interface CustomerDAO {

	List<Customer> findAll();
}
