package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.test.util.AbstractAdminOperationsTest;

@SuppressWarnings("deprecation")
public class InitializeDBControllerTest extends AbstractAdminOperationsTest {
    private InitializeDBController controller;
    protected AdminManager adminManager;
    
    public final void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
    
	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
    
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        controller = new InitializeDBController();
        controller.setAdminManager(adminManager);
    }
    
    @Test
    public void testHandleRequest() throws Exception {
        assertDBInitialized(new DBInitializer() {
            public void initializeDB() throws Exception {
                controller.handleRequest(new MockHttpServletRequest(), new MockHttpServletResponse());                
            }
        });
    }
    
    @Test
    public void testInitializationError() throws Exception {
        controller.setAdminManager(null);
        ModelAndView mav = controller.handleRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
        Assert.assertEquals("error", mav.getViewName());
        controller.setAdminManager(adminManager);
    }
}
