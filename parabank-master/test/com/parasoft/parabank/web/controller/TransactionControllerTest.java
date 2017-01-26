package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Transaction;

public class TransactionControllerTest
extends AbstractBankControllerTest<TransactionController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}

	@Test
    public void testHandleRequest() throws Exception {
        request.setParameter("id", "12367");
        ModelAndView mav = controller.handleRequest(request, response);
        
        Assert.assertEquals("transaction", mav.getViewName());
        Transaction transaction = (Transaction)mav.getModel().get("transaction");
        Assert.assertEquals(12367, transaction.getId());
    }
        
	@Test
    public void testHandleInvalidRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        
        Assert.assertEquals("error", mav.getViewName());
        Assert.assertEquals("error.missing.transaction.id", getModelValue(mav, "message"));
        
        request.setParameter("id", "str");
        mav = controller.handleRequest(request, response);
        
        Assert.assertEquals("error", mav.getViewName());
        Assert.assertEquals("error.invalid.transaction.id", getModelValue(mav, "message"));
        
        request.setParameter("id", "0");
        mav = controller.handleRequest(request, response);
        Assert.assertEquals("error.invalid.transaction.id", getModelValue(mav, "message"));
    }
}
