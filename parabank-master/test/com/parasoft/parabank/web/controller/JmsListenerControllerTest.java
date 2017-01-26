package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.messaging.MockJmsListeningContainer;

public class JmsListenerControllerTest
extends AbstractControllerTest<JmsListenerController> {
    private AdminManager adminManager;
    private MockJmsListeningContainer jmsListener;
    
    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
    
    public void setJmsListener(MockJmsListeningContainer jmsListener) {
        this.jmsListener = jmsListener;
    }
    
	@Before
	public void before() throws Exception
	{
		setUp();
	}
    
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        controller.setAdminManager(adminManager);
    }

    @Test
    public void testHandleBadRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertEquals("error", mav.getViewName());
    }
    
    @Test
    public void testHandleStartup() throws Exception {
        jmsListener.setListenerRunning(false);
        jmsListener.setListenerInitialized(false);
        request.setParameter("shutdown", "false");
        ModelAndView mav = controller.handleRequest(request, response);
        assertNull(mav);
        Assert.assertEquals("admin.htm", response.getRedirectedUrl());
        Assert.assertTrue(jmsListener.isListenerRunning());
        Assert.assertTrue(jmsListener.isListenerInitialized());
    }
    
    @Test
    public void testHandleShutdown() throws Exception {
        jmsListener.setListenerRunning(true);
        jmsListener.setListenerInitialized(true);
        request.setParameter("shutdown", "true");
        ModelAndView mav = controller.handleRequest(request, response);
        Assert.assertNull(mav);
        Assert.assertEquals("admin.htm", response.getRedirectedUrl());
        Assert.assertFalse(jmsListener.isListenerRunning());
        Assert.assertFalse(jmsListener.isListenerInitialized());
    }
}
