package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;


public class PortfolioControllerTest 
    extends AbstractControllerTest<PortfolioController>{

	@Before
	public void before() throws Exception
	{
		setUp();
	}

	@Test
    public void testHandleRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(null, null);      
        Assert.assertEquals("portfolio", mav.getViewName());
        Assert.assertNotNull(mav.getModel());
    }

}
