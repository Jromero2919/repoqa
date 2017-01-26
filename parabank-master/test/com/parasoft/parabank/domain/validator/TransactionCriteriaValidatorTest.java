package com.parasoft.parabank.domain.validator;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.validation.Validator;

import com.parasoft.parabank.domain.TransactionCriteria;
import com.parasoft.parabank.domain.TransactionCriteria.SearchType;

public class TransactionCriteriaValidatorTest extends TestCase {
    private Validator validator;
    private TransactionCriteria criteria;
    
    @Override
    protected void setUp() throws Exception {
        validator = new TransactionCriteriaValidator();
        criteria = new TransactionCriteria();
    }
    
    @Test
    public void testSupports() {
        assertTrue(validator.supports(TransactionCriteria.class));
        assertFalse(validator.supports(Object.class));
    }
    
    @Test
    public void testValidateId() throws Exception {
        criteria.setSearchType(SearchType.ID);
        AbstractValidatorTest.assertValidate(validator, criteria, 
                new String[] { "transactionId" });
    }
    
    @Test
    public void testValidateDate() throws Exception {
        criteria.setSearchType(SearchType.DATE);
        AbstractValidatorTest.assertValidate(validator, criteria, 
                new String[] { "onDate" });
    }
    
    @Test
    public void testValidateDateRange() throws Exception {
        criteria.setSearchType(SearchType.DATE_RANGE);
        AbstractValidatorTest.assertValidate(validator, criteria,
                new String[] { "toDate", "fromDate" });
    }
    
    @Test
    public void testValidateAmount() throws Exception {
        criteria.setSearchType(SearchType.AMOUNT);
        AbstractValidatorTest.assertValidate(validator, criteria,
                new String[] { "amount" });
    }
}
