package com.vodafone.service;

import java.util.List;

import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Bill;

public interface BillService {
	List<Bill> findAllBills(long customerID) throws UserNotFoundException;
}
