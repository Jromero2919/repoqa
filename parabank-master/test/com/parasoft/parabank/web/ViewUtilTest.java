package com.parasoft.parabank.web;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.AbstractModelAndViewTests;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings({"deprecation", "rawtypes"})
public class ViewUtilTest extends AbstractModelAndViewTests {
    private static final String ERROR_MESSAGE = "error message";
    private static final String[] PARAMS = new String[] { "param1", "param2" };
    
    @Test
    public void before() throws Exception
    {
    	setUp();
    }
    
    @Test
    public void testCreateErrorView() {
        ModelAndView mav = ViewUtil.createErrorView(ERROR_MESSAGE);
        Assert.assertEquals("error", mav.getViewName());
        assertViewName(mav, "error");
        Map map = assertAndReturnModelAttributeOfType(mav, "model", Map.class);
        Assert.assertEquals(ERROR_MESSAGE, map.get("message"));
        Assert.assertNull(map.get("parameters"));
        
        mav = ViewUtil.createErrorView(ERROR_MESSAGE, PARAMS);
        Assert.assertEquals("error", mav.getViewName());
        assertViewName(mav, "error");
        map = assertAndReturnModelAttributeOfType(mav, "model", Map.class);
        Assert.assertEquals(ERROR_MESSAGE, map.get("message"));
        Assert.assertEquals(PARAMS, map.get("parameters"));
    }
}
