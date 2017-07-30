package com.vodafone.customer;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.vodafone.dao.CustomerDAO;
import com.vodafone.dao.CustomerDAOImpl;
import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.model.FullName;
import com.vodafone.service.CustomerServiceImpl;

public class CustomerServiceImplTest extends AbstractMvcTest{

	@InjectMocks
    private CustomerServiceImpl testingObject;

    @Mock
    private CustomerDAO customerDAO;

    @Spy
    private CustomerDAOImpl customerDAOImpl;

/*    @BeforeMethod(alwaysRun = true)
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }*/
    
    @Test
    public void testGetOrderService() throws UserNotFoundException{
    	List<Customer> customers = new ArrayList<Customer>();
		Customer customer1 = new Customer();
		customer1.setId(1l);
		FullName fullname1 = new FullName();
		fullname1.setFirstName("Moh1");
	    fullname1.setMiddleName("Ali1");
		fullname1.setLastName("Rizk1");
		customer1.setFullName(fullname1);
		customers.add(customer1);
    	// here to should call doReturn().when() on spy or mock objects 
    	// so when you use test object to behave as you would like then
		doReturn(customers).when(customerDAO).findAll();
		// or use this syntax however doReturn..when is the best practice
		//when(customerDAO.findAll()).thenReturn(customers);
        //call testing method on testing object
    	List<Customer> customertested = testingObject.findAllCustomers();
    	
        System.out.println("customer is "+ customertested);
        Assert.assertEquals(customers,customertested);
    }
}
