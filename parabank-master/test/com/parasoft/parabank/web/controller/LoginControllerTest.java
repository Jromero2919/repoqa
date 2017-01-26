package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.web.UserSession;


public class LoginControllerTest
extends AbstractBankControllerTest<LoginController> {

	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
	public void onSetUp() throws Exception {
		super.onSetUp();
		controller.setAccessModeController(amc);
	}

	@Test
    public void testHandleForward() throws Exception {
        assertGetRequest("overview.htm");     
    }
    
	@Test
    public void testHandleRedirect() throws Exception {
        request.setParameter("forwardAction", "page.htm");
        assertGetRequest("page.htm");
    }
    
	@Test
    public void assertGetRequest(String url) throws Exception {
        request.setParameter("username", "john");
        request.setParameter("password", "demo");
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertNull(mav);
        Assert.assertEquals(url, response.getRedirectedUrl());
        UserSession session = (UserSession)request.getSession().getAttribute("userSession");
        Assert.assertNotNull(session);
        Assert.assertEquals(12212, session.getCustomer().getId());
    }
    
	@Test
    public void testHandleBadGetRequest() throws Exception {
        assertError("error.empty.username.or.password");
        
        request.setParameter("username", "user");
        assertError("error.empty.username.or.password");
        
        request.setParameter("username", "");
        request.setParameter("password", "pass");
        assertError("error.empty.username.or.password");
        
        request.setParameter("username", "user");
        request.setParameter("password", "pass");
        assertError("error.invalid.username.or.password");
    }
    
    public void assertError(String message) throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertEquals("error", mav.getViewName());
        Assert.assertEquals(message, getModelValue(mav, "message"));
    }
}
