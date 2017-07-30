package com.vodafone.customer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.Mock;

import com.vodafone.exception.UserNotFoundException;
import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;
import com.vodafone.service.CustomerServiceImpl;
/*
 * link for mockito method and explaintion
 * http://www.vogella.com/tutorials/Mockito/article.html
 * https://dzone.com/articles/use-mockito-mock-autowired
 * */
public class ServiceTest extends AbstractMvcTest{
	@Mock
	CustomerService customerServiceMoc = mock(CustomerServiceImpl.class);
	@Test
	public void testFindById() throws UserNotFoundException{
		
			doReturn(new Customer()).when(customerServiceMoc).findById(52);
			Customer c = customerServiceMoc.findById(52);
			System.out.println(c);
			assertEquals(c ,new Customer());		               
	}
	@Test
	public void testFindByIdException(){	
			try {
				doThrow(new UserNotFoundException()).when(customerServiceMoc).findById(52);
				Customer c = customerServiceMoc.findById(52);
				// never reaches this line since its defined as it throws exception
				System.out.println("returned user "+c);
				assertEquals(c ,new Customer());	
			} catch (UserNotFoundException e) {
				System.out.println("the use isn't found");
			}               
	}
	
}
