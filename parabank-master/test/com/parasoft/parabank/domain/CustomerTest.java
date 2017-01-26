package com.parasoft.parabank.domain;

import org.junit.Test;

import com.parasoft.parabank.test.util.AbstractBeanTestCase;

import static org.junit.Assert.*;

public class CustomerTest extends AbstractBeanTestCase<Customer> {
	
	
	@Test
    public void testGetFullName() {
        bean.setFirstName("first");
        bean.setLastName("last");
        assertEquals("first last", bean.getFullName());
    }
}
