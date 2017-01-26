package com.parasoft.parabank.domain.validator;

import org.junit.Test;
import org.springframework.validation.Validator;

import com.parasoft.parabank.domain.Address;

public class AddressValidatorTest extends AbstractValidatorTest {
    public AddressValidatorTest() {
        super(Address.class, new String[] { "street", "city", "state", "zipCode" });
    }
    
    @Override
    protected Validator getValidator() {
        return new AddressValidator();
    }
    
    
    @Test
    public void testSupports() {
    	testSupportsImpl();
    }
    
    @Test
    public void testValidate() throws Exception {
    	testValidateImpl();
    }
}
