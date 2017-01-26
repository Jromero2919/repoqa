package com.parasoft.parabank.web.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Account;
import com.parasoft.parabank.domain.LoanResponse;
import com.parasoft.parabank.web.form.RequestLoanForm;

@SuppressWarnings({"deprecation", "unchecked"})
public class RequestLoanControllerTest
extends AbstractBankControllerTest<RequestLoanController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        controller.setCommandClass(RequestLoanForm.class);
        registerSession();
    }

    @Test
    public void testHandleGetRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);

        List<Account> accounts = (List<Account>)mav.getModel().get("accounts");
        Assert.assertEquals(11, accounts.size());
    }
    
    @Test
    public void testOnSubmit() throws Exception {
        final int FROM_ACCOUNT_ID = 12345;
        
        RequestLoanForm form = new RequestLoanForm();
        form.setAmount(new BigDecimal("1000.00"));
        form.setDownPayment(new BigDecimal("100.00"));
        form.setFromAccountId(FROM_ACCOUNT_ID);
        
        BindException errors = new BindException(form, "requestLoanForm");
        ModelAndView mav = controller.onSubmit(request, response, form, errors);
        Assert.assertEquals("requestloanConfirm", mav.getViewName());
        
        LoanResponse response = (LoanResponse)mav.getModel().get("loanResponse");
        Assert.assertTrue(response.isApproved());
        Assert.assertNotNull(response.getResponseDate());
        Assert.assertTrue(response.getAccountId() > FROM_ACCOUNT_ID);
    }
        
    @Test
    public void testOnBindAndValidate() throws Exception {
        RequestLoanForm form = new RequestLoanForm();
        form.setAmount(null);
        BindException errors = new BindException(form, "requestLoanForm");
        controller.onBindAndValidate(request, form, errors);
        Assert.assertEquals(2, errors.getErrorCount());
        Assert.assertNotNull(errors.getFieldError("amount"));
        Assert.assertNotNull(errors.getFieldError("downPayment"));
    }
}
