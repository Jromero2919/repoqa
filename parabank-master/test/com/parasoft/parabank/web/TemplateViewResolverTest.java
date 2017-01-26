package com.parasoft.parabank.web;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;

@SuppressWarnings("deprecation")
public class TemplateViewResolverTest extends AbstractDependencyInjectionSpringContextTests {
    private ViewResolver viewResolver;
    
    public void setViewResolver(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }
    
    @Before
    public void before() throws Exception
    {
    	setUp();
    }
    
    @Override
    protected final String[] getConfigLocations() {
        return new String[] { "classpath:test-context.xml" };
    }
    
    @Test
    public void testResolveViewName() throws Exception {
        View view = viewResolver.resolveViewName("test", Locale.getDefault());
        Assert.assertTrue(view instanceof AbstractView);
        AbstractView abstractView = (AbstractView)view;
        Assert.assertEquals(TemplateViewResolver.TEMPLATE_VIEW_NAME,
                abstractView.getBeanName());
        Map<String, Object> attributes = abstractView.getAttributesMap();
        Assert.assertEquals("test", attributes.get(TemplateViewResolver.VIEW_ATTRIBUTE));
    }
}
