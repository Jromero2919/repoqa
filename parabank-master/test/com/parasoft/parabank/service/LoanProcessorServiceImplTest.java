package com.parasoft.parabank.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.parasoft.parabank.domain.LoanRequest;
import com.parasoft.parabank.domain.LoanResponse;
import com.parasoft.parabank.test.util.AbstractParaBankDataSourceTest;

@SuppressWarnings("deprecation")
public class LoanProcessorServiceImplTest extends AbstractParaBankDataSourceTest {    
    private LoanProcessorService loanProcessorService;
    private LoanRequest loanRequest;

    public void setLoanProcessorService(LoanProcessorService loanProcessorService) {
        this.loanProcessorService = loanProcessorService;
    }
    
    @Before
    public void before() throws Exception
    {
    	setUp();
    }
    
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        loanRequest = new LoanRequest();
        loanRequest.setCustomerId(12345);
        loanRequest.setAvailableFunds(new BigDecimal("1000.00"));
    }
    
    @Test
    public void testRequestLoan() throws Exception {
        loanRequest.setLoanAmount(new BigDecimal("100.00"));
        loanRequest.setDownPayment(new BigDecimal("10.00"));
        LoanResponse response = loanProcessorService.requestLoan(loanRequest);
        Assert.assertTrue(response.isApproved());
        Assert.assertNotNull(response.getResponseDate());
        Assert.assertEquals("Test Provider", response.getLoanProviderName());
        
        loanRequest.setLoanAmount(new BigDecimal("10000.00"));
        response = loanProcessorService.requestLoan(loanRequest);
        Assert.assertFalse(response.isApproved());
        Assert.assertNotNull(response.getResponseDate());
        Assert.assertEquals("error.insufficient.funds", response.getMessage());
        Assert.assertEquals("Test Provider", response.getLoanProviderName());
    }
}
