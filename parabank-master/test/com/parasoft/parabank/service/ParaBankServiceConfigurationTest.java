package com.parasoft.parabank.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ParaBankServiceConfigurationTest extends TestCase {
    private ParaBankServiceConfiguration configuration;
    
    
    @Before
    public void before() throws Exception
    {
    	setUp();
    }
    
    @Override
    protected void setUp() throws Exception {
        configuration = new ParaBankServiceConfiguration();
    }
    
    @Test
    public void testGetWrapperPartMinOccurs() {
        Assert.assertEquals(new Long(1), configuration.getWrapperPartMinOccurs(null));
    }
    
    @Test
    public void testIsWrapperPartNillable() {
        Assert.assertEquals(Boolean.FALSE, configuration.isWrapperPartNillable(null));
    }
}
