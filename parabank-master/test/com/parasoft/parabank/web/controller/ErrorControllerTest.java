package com.parasoft.parabank.web.controller;

import org.apache.cxf.interceptor.Fault;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.parasoft.parabank.service.ParaBankServiceException;

public class ErrorControllerTest
extends AbstractControllerTest<ErrorController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
	
	@Test
    public void testHandleRequest() throws Exception {
        request.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, 404);
        assertError("error.not.found");
        
        request.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, 500);
        assertError("error.internal");
        
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE,
                new Exception(new Fault(new ParaBankServiceException("error message"))));
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertNull(mav);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("error message", response.getContentAsString());
    }
    
    private void assertError(String message) throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertEquals("error", mav.getViewName());
        Assert.assertEquals(message, getModelValue(mav, "message"));
    }
}
