package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.web.form.CustomerForm;

public class UpdateCustomerControllerTest
extends AbstractCustomerControllerTest<UpdateCustomerController> {

	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        registerSession();
    }
    
    @Test
    public void testOnSubmit() throws Exception {
        CustomerForm form = getCustomerForm();
        BindException errors = new BindException(form, "customerForm");
        ModelAndView mav = controller.onSubmit(request, response, form, errors);
        Assert.assertEquals("updateprofileConfirm", mav.getViewName());
        Customer customer = (Customer)mav.getModel().get("customer");
        Assert.assertEquals(12212, customer.getId());
    }
    
    @Override
    protected CustomerForm createCustomerForm() throws Exception {
        return (CustomerForm)controller.formBackingObject(request);
    }
}
