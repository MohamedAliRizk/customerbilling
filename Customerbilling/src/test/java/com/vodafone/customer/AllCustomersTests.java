
package com.vodafone.customer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class AllCustomersTests extends AbstractMvcTest {
	
    @Test
    public void getAllCustomer_authorized() {
		try {
			final String token = extractToken(login("bill", "abc123").andReturn());
			mockMvc.perform(MockMvcRequestBuilders.get("/customers")
					.header("Authorization", token))
					.andExpect(status().isOk());//.andExpect(content().json("[{\"id\": 1,\"fullName\": {\"firstName\": \"Moh1\",\"middleName\": \"Ali1\",\"lastName\": \"Rizk1\"},\"age\": 0,\"gender\": null,\"address\": null,\"mobileNumber\": null}]"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void getAllCustomer_Unauthorized() {
		    try {
		    	final String token = extractToken(login("tom", "abc123").andReturn());
		    	mockMvc.perform(MockMvcRequestBuilders.get("/customers")
		    			.header("Authorization", token))
		    	.andExpect(status().isOk());
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    @Test
    public void getAllCustomer_Unathenticated() {
		    try {
		    	final String token = extractToken(login("act", "abc1").andReturn());
		    	mockMvc.perform(MockMvcRequestBuilders.get("/customers")
		    			.header("Authorization", token))
		    			.andExpect(status().isUnauthorized());
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

}