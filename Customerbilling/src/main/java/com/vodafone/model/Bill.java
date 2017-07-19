package com.vodafone.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Bill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Customer customer;
	private double paid;
	private Date dueDate;
	
	public Bill(){
		
	}
	public Bill(long id, Customer customer, double paid, Date dueDate) {
		super();
		this.id = id;
		this.customer = customer;
		this.paid = paid;
		this.dueDate = dueDate;
	}
	
}
