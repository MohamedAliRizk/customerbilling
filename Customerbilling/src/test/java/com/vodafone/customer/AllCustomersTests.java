
package com.vodafone.customer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import com.vodafone.CustomerbillingApplication;
import com.vodafone.controller.CustomerRestController;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
public class AllCustomersTests {
	protected MockMvc mockMvc;

	@Autowired
    private WebApplicationContext wac;

	    @Before
	    public void before() {
	    	mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

	    }
   

    
    @Test
    public void getAllCustomer_authorized() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/Customers").header("Authorization", "Basic "
				+ new String(Base64Utils.encode("act:act".getBytes()))
	    		    )).andExpect(status().isOk()).andExpect(content().json("[{\"id\": 1,\"fullName\": {\"firstName\": \"Moh1\",\"middleName\": \"Ali1\",\"lastName\": \"Rizk1\"},\"age\": 0,\"gender\": null,\"address\": null,\"mobileNumber\": null}]"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		    
    	
    }
    
    @Test
    public void getAllCustomer_Unauthorized() {
    	
		    try {
//				mockMvc.perform(get("/Customers").param("username", "userer")
//				        .param("password", "78a0d927-db38-4c85-94ed-c2558c6054f3err").with(csrf())).andExpect(status().isUnauthorized());
		    	mockMvc.perform(MockMvcRequestBuilders.get("/Customers").header("Authorization", "Basic "
				+ new String(Base64Utils.encode("act:acjdjjd".getBytes()))
		    		    )).andExpect(status().isUnauthorized());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    		    
    	
    }


}

























//import static org.junit.Assert.assertEquals;
//
//import java.io.IOException;
//
//import org.apache.http.*;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.vodafone.controller.CustomerRestController;
//
//import org.springframework.http.HttpStatus;
//import org.apache.log4j.Logger;

//@RunWith(SpringRunner.class)
//@WebMvcTest(CustomerRestController.class)
//public class AllCustomersTests {
//
//	@Test
//	public void givenUnauthorized_whenNonAuthorisedUserTryToAcess_then401IsReceived() {
//
//		Logger logger = Logger.getLogger(AllCustomersTests.class);
//
//		try {
//			// DefaultHttpClient httpClient;
//			// URL url = new URL("http://localhost:8080");
//			CredentialsProvider credsProvider = new BasicCredentialsProvider();
//			credsProvider.setCredentials(new AuthScope("http://localhost", 8080),
//					new UsernamePasswordCredentials("user", "passw0rd"));
//
//			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();
//			HttpResponse response;
//			response = client.execute(new HttpGet("http://localhost:8080/Customers"));
//			assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.UNAUTHORIZED);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			logger.error("internal error " + e);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error("internal error " + e);
//		}
//		//
//
//	}
//
//}





















//import static org.junit.Assert.assertEquals;
//
//import java.io.IOException;
//
//import org.apache.http.*;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.vodafone.controller.CustomerRestController;
//
//import org.springframework.http.HttpStatus;
//import org.apache.log4j.Logger;

//@RunWith(SpringRunner.class)
//@WebMvcTest(CustomerRestController.class)
//public class AllCustomersTests {
//
//	@Test
//	public void givenUnauthorized_whenNonAuthorisedUserTryToAcess_then401IsReceived() {
//
//		Logger logger = Logger.getLogger(AllCustomersTests.class);
//
//		try {
//			// DefaultHttpClient httpClient;
//			// URL url = new URL("http://localhost:8080");
//			CredentialsProvider credsProvider = new BasicCredentialsProvider();
//			credsProvider.setCredentials(new AuthScope("http://localhost", 8080),
//					new UsernamePasswordCredentials("user", "passw0rd"));
//
//			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();
//			HttpResponse response;
//			response = client.execute(new HttpGet("http://localhost:8080/Customers"));
//			assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.UNAUTHORIZED);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			logger.error("internal error " + e);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error("internal error " + e);
//		}
//		//
//
//	}
//
//}
