package com.parasoft.parabank.web.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.Account;
import com.parasoft.parabank.domain.Account.AccountType;
import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.domain.logic.AdminParameters;
import com.parasoft.parabank.domain.Transaction;
import com.parasoft.parabank.web.form.OpenAccountForm;

@SuppressWarnings({"deprecation", "unchecked"})
public class OpenAccountControllerTest
extends AbstractBankControllerTest<OpenAccountController> {
    private AdminManager adminManager;
    
    public void setAdminManager(AdminManager adminManager) {
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
        controller.setCommandClass(OpenAccountForm.class);
        controller.setAdminManager(adminManager);
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
        Assert.assertEquals(adminManager.getParameter(AdminParameters.MINIMUM_BALANCE), 
                mav.getModel().get("minimumBalance"));
    }
    
    @Test
    public void testOnSubmit() throws Exception {
        OpenAccountForm form = new OpenAccountForm();
        form.setFromAccountId(12345);
        form.setType(AccountType.CHECKING);
        
        Account oldAccount = bankManager.getAccount(12345);
        List<Transaction> transactions = bankManager.getTransactionsForAccount(oldAccount);
        Assert.assertEquals(7, transactions.size());
        
        BindException errors = new BindException(form, "openAccountForm");
        ModelAndView mav = controller.onSubmit(request, response, form, errors);
        
        Assert.assertEquals("openaccountConfirm", mav.getViewName());
        Account newAccount = (Account)mav.getModel().get("account");
        Assert.assertEquals(13566, newAccount.getId());
        
        newAccount = bankManager.getAccount(newAccount.getId());
        Assert.assertEquals(new BigDecimal(adminManager.getParameter(AdminParameters.MINIMUM_BALANCE)),
                newAccount.getBalance());
        
        transactions = bankManager.getTransactionsForAccount(oldAccount);
        Assert.assertEquals(8, transactions.size());
        
        transactions = bankManager.getTransactionsForAccount(newAccount);
        Assert.assertEquals(1, transactions.size());
    }
}
