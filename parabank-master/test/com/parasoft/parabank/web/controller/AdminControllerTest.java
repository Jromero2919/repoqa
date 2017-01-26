package com.parasoft.parabank.web.controller;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.domain.logic.impl.ConfigurableLoanProvider;
import com.parasoft.parabank.web.form.AdminForm;

@SuppressWarnings("deprecation")
public class AdminControllerTest
extends AbstractValidatingBankControllerTest<AdminController> {
    private AdminManager adminManager;
    private ConfigurableLoanProvider loanProvider;
    
    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
    
    public void setLoanProvider(ConfigurableLoanProvider loanProvider) {
        this.loanProvider = loanProvider;
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
        controller.setLoanProvider(loanProvider);
        controller.setLoanProcessor(loanProvider);
        controller.setCommandClass(AdminForm.class);
    }
    
    @Test
    public void testHandleGetRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        boolean isJmsRunning = (Boolean)mav.getModel().get("isJmsRunning");
        Assert.assertFalse(isJmsRunning);
    }
    
    @Test
    public void testOnSubmit() throws Exception {
        AdminForm form = (AdminForm)controller.formBackingObject(new MockHttpServletRequest());
        
        BindException errors = new BindException(form, "adminForm");
        ModelAndView mav = controller.onSubmit(request, response, form, errors);
        Assert.assertEquals("settings.saved", mav.getModel().get("message").toString());
    }
    
    @Test
    public void testOnBindAndValidate() throws Exception {
        AdminForm form = getAdminForm();
        form.setInitialBalance(null);
        assertError(form, "initialBalance");
        
        form = getAdminForm();
        form.setMinimumBalance(null);
        assertError(form, "minimumBalance");
        
        form = getAdminForm();
        form.setLoanProcessorThreshold(null);
        assertError(form, "loanProcessorThreshold");
    }
    
    private AdminForm getAdminForm() {
        AdminForm form = new AdminForm();
        form.setInitialBalance(new BigDecimal("1111.11"));
        form.setMinimumBalance(new BigDecimal("2222.22"));
        form.setLoanProcessorThreshold(3333);
        return form;
    }
}
