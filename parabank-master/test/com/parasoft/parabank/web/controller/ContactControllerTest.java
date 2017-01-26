package com.parasoft.parabank.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.web.form.ContactForm;

public class ContactControllerTest
extends AbstractValidatingBankControllerTest<ContactController> {
	
	@Before
	public void before() throws Exception
	{
		setUp();
	}
	
	@Test
    public void testOnSubmit() throws Exception {
        ModelAndView mav = controller.onSubmit(getContactForm());
        Assert.assertEquals("contactConfirm", mav.getViewName());
        Assert.assertEquals("customer name", getModelValue(mav, "name"));
    }
    
	@Test
    public void testOnBindAndValidate() throws Exception {
        ContactForm form = new ContactForm();
        form.setName(null);
        assertError(form, "name", "email", "phone", "message");
        
        form = getContactForm();
        form.setName(null);
        assertError(form, "name");
        
        form = getContactForm();
        form.setEmail(null);
        assertError(form, "email");
        
        form = getContactForm();
        form.setPhone(null);
        assertError(form, "phone");
        
        form = getContactForm();
        form.setMessage(null);
        assertError(form, "message");
    }
    
    private ContactForm getContactForm() {
        ContactForm form = new ContactForm();
        form.setName("customer name");
        form.setEmail("customer email");
        form.setPhone("customer phone");
        form.setMessage("customer message");
        return form;
    }
}
