package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Customer;
import com.parasoft.parabank.web.UserSession;
import com.parasoft.parabank.web.form.CustomerForm;

public class RegisterCustomerControllerTest
extends AbstractCustomerControllerTest<RegisterCustomerController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}


	@Test
    public void testOnSubmit() throws Exception {
        CustomerForm form = getCustomerForm();
        BindException errors = new BindException(form, "customerForm");
        ModelAndView mav = controller.onSubmit(request, response, form, errors);
        Assert.assertEquals("registerConfirm", mav.getViewName());
        Customer customer = (Customer)mav.getModel().get("customer");
        Assert.assertEquals(12434, customer.getId());
        
        UserSession session = (UserSession)request.getSession().getAttribute("userSession");
        Assert.assertNotNull(session);
        Assert.assertEquals(customer, session.getCustomer());
    }
    
	@Test
    public void testDuplicateUsername() throws Exception {
        CustomerForm form = getCustomerForm();
        form.getCustomer().setUsername("john");
        BindException errors = new BindException(form, "customerForm");
        controller.onSubmit(request, response, form, errors);
        Assert.assertEquals(1, errors.getErrorCount());
        Assert.assertNotNull(errors.getFieldError("customer.username"));
    }
    
	@Test
    public void testOnBindAndValidate() throws Exception {
        super.testOnBindAndValidate();
        
        CustomerForm form = getCustomerForm();
        form.setRepeatedPassword(null);
        assertError(form, "repeatedPassword");
        
        form = getCustomerForm();
        form.setRepeatedPassword("password");
        assertError(form, "repeatedPassword");
    }
    
    protected CustomerForm createCustomerForm() throws Exception {
        return (CustomerForm)controller.formBackingObject(request);
    }
}
