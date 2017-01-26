package com.parasoft.parabank.dao.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

import com.parasoft.parabank.dao.AdminDao;
import com.parasoft.parabank.dao.InMemoryAdminDao;

import static org.junit.Assert.*;

public class InMemoryAdminDaoTest 
{
    private static final String NAME1 = "name1";   
    private static final String VALUE1 = "value1";
    private static final String NAME2 = "name2";   
    private static final String VALUE2 = "value2";
    
    private AdminDao adminDao;

    @Before
    public void setUp() throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();
        
        parameters.put(NAME1, VALUE1);
        parameters.put(NAME2, VALUE2);
        
        adminDao = new InMemoryAdminDao(parameters);
    }
    
    @Test
    public void testGetParameter() {
        assertEquals(VALUE1, adminDao.getParameter(NAME1));
        assertEquals(VALUE2, adminDao.getParameter(NAME2));
                
        assertNull(adminDao.getParameter(null));
        assertNull(adminDao.getParameter(""));
        assertNull(adminDao.getParameter("unknown"));
    }
    
    @Test
    public void testSetParameter() {
        assertEquals(VALUE1, adminDao.getParameter(NAME1));
        adminDao.setParameter(NAME1, VALUE2);
        assertEquals(VALUE2, adminDao.getParameter(NAME1));
    }
    
    @Test
    public void testGetParameters() {
        assertNotNull(adminDao.getParameters());
        assertEquals(2, adminDao.getParameters().size());
    }
    
    @Test
    public void testNullMethods() {
        adminDao.initializeDB();
        adminDao.cleanDB();
    }
}
