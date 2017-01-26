package com.parasoft.parabank.domain.validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.parasoft.parabank.test.util.AbstractBeanTestCase;

public abstract class AbstractValidatorTest 
{
    private Class<?> beanClass;
    private String[] requiredFields;
    
    public AbstractValidatorTest(Class<?> beanClass, String[] requiredFields) {
        this.beanClass = beanClass;
        this.requiredFields = requiredFields;
    }
    
    protected abstract Validator getValidator();

    protected void testSupportsImpl() {
        Assert.assertTrue(getValidator().supports(beanClass));
        Assert.assertFalse(getValidator().supports(Object.class));
    }
    
    protected void testValidateImpl() throws Exception {
        BeanWrapper wrapper = new BeanWrapperImpl(beanClass);
        assertValidate(getValidator(), wrapper, requiredFields);
    }
    
    static final void assertValidate(Validator validator,
            Object target, String[] requiredFields) throws Exception { 
        Set<String> requiredFieldSet = new HashSet<String>(Arrays.asList(requiredFields));
        Field[] fields = target.getClass().getDeclaredFields();
        int expectedErrorCount = fields.length;
        
        for (Field field : fields) {
            if (!requiredFieldSet.contains(field.getName())) {
                expectedErrorCount--;
            }
            if (!field.getType().isEnum() && field.getType().getPackage() != null && 
                    !field.getType().getPackage().getName().startsWith("java")) {
                expectedErrorCount += field.getType().getDeclaredFields().length;
            }
        }
        
        for (Field field : fields) {
            if (!requiredFieldSet.contains(field.getName())) {
                continue;
            }

            field.setAccessible(true);
            
            BindException errors = new BindException(target, "target");
            
            validator.validate(target, errors);
            Assert.assertNull(field.get(target));
            Assert.assertNotNull("expected error for empty field: " + field.getName(),
                    errors.getFieldError(field.getName()));
            Assert.assertEquals(expectedErrorCount, errors.getErrorCount());

            AbstractBeanTestCase.toggleField(field, target, true);
            
            errors = new BindException(target, "target");
            validator.validate(target, errors);
            Assert.assertNotNull(field.get(target));
            Assert.assertNull("expected error for non-empty field: " + field.getName(),
                    errors.getFieldError(field.getName()));
            Assert.assertEquals(--expectedErrorCount, errors.getErrorCount());
        }
    }
}
