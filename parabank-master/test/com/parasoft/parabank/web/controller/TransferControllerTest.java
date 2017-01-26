package com.parasoft.parabank.web.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Account;
import com.parasoft.parabank.web.form.TransferForm;

@SuppressWarnings({"deprecation", "unchecked"})
public class TransferControllerTest
extends AbstractBankControllerTest<TransferController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        controller.setCommandClass(TransferForm.class);
        registerSession();
    }

    @Test
    public void testHandleGetRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        assertReferenceData(mav);
    }
    
    private void assertReferenceData(ModelAndView mav) {
        List<Account> accounts = (List<Account>)mav.getModel().get("accounts");
        Assert.assertEquals(11, accounts.size());
    }
    
    @Test
    public void testOnSubmit() throws Exception {
        TransferForm form = new TransferForm();
        form.setAmount(new BigDecimal(100));
        form.setFromAccountId(12345);
        form.setToAccountId(54321);
        
        ModelAndView mav = controller.onSubmit(form);
        Assert.assertEquals("transferConfirm", mav.getViewName());
        Assert.assertEquals(new BigDecimal(100), getModelValue(mav, "amount"));
        Assert.assertEquals(12345, getModelValue(mav, "fromAccountId"));
        Assert.assertEquals(54321, getModelValue(mav, "toAccountId"));
    }
        
    @Test
    public void testOnBindAndValidate() throws Exception {
        TransferForm form = new TransferForm();
        form.setAmount(null);
        BindException errors = new BindException(form, "transferForm");
        controller.onBindAndValidate(request, form, errors);
        Assert.assertEquals(1, errors.getErrorCount());
        Assert.assertNotNull(errors.getFieldError("amount"));
    }
}
