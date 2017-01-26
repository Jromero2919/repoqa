package com.parasoft.parabank.web.controller;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parasoft.parabank.domain.News;
import com.parasoft.parabank.domain.logic.NewsManager;

@SuppressWarnings({"unchecked", "deprecation"})
public class IndexControllerTest
extends AbstractControllerTest<IndexController> {
    private NewsManager newsManager;
    
    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }
	
    @Before
	public void before() throws Exception
	{
		setUp();
	}
	
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        controller.setNewsManager(newsManager);
    }
    
    @Test
    public void testHandleGetRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(request, response);
        
        Assert.assertEquals("index", mav.getViewName());
        
        Date date = (Date)getModelValue(mav, "date");
        Assert.assertEquals("2010-09-13", date.toString());
        
        List<News> news = (List<News>)getModelValue(mav, "news");
        Assert.assertEquals(3, news.size());
    }
    
    @Test
    public void testDatabaseUninitialized() throws Exception {
        getJdbcTemplate().execute("DROP TABLE News");
        ModelAndView mav = controller.handleRequest(request, response);
        assertNull(mav);
        Assert.assertEquals("initializeDB.htm", response.getRedirectedUrl());
    }
}
