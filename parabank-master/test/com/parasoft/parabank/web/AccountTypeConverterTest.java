package com.parasoft.parabank.web;

import org.junit.Test;

import com.parasoft.parabank.domain.Account.AccountType;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class AccountTypeConverterTest {
	
	@Test
    public void testConvert() {
        AccountTypeConverter converter = new AccountTypeConverter();
        assertNull(converter.convert(null));
        assertEquals(AccountType.CHECKING, converter.convert("CHECKING"));
        assertEquals(AccountType.SAVINGS, converter.convert("SAVINGS"));
        
        try {
            converter.convert("");
            fail("did not catch expected IllegalArgumentException");
        } catch (IllegalArgumentException e) { }
    }
}
