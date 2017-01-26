package com.parasoft.parabank.domain.logic.impl;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.test.util.AbstractAdminOperationsTest;

public class AdminManagerImplTest extends AbstractAdminOperationsTest {
    private static final String TEST_PARAMETER = "loanProcessorThreshold";
    private static final String EXPECTED_VALUE = "20";
    
    private AdminManager adminManager;
    
    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
    
    @Before
    public void before() throws Exception
    {
    	setUp();
    }
    
    
    @Test
    public void testInitializeDB() throws Exception {
        assertDBInitialized(new DBInitializer() {
            public void initializeDB() throws Exception {
                adminManager.initializeDB();
            }
        });
    }

    @Test
    public void testCleanDB() throws Exception {
        assertDBClean(new DBCleaner() {
            public void cleanDB() throws Exception {
                adminManager.cleanDB();
            }
        });
    }
    
    @Test
    public void testStartupJmsListener() {
        assertJmsStartup(new JmsStartupManager() {
            public void startupJmsListener() {
                adminManager.startupJmsListener();
            }
        });
    }
    
    @Test
    public void testShutdownJmsListener() {
        assertJmsShutdown(new JmsShutdownManager() {           
            public void shutdownJmsListener() {
                adminManager.shutdownJmsListener();
            }
        });
    }
    
    @Test
    public void testGetParameter() {
        assertEquals(EXPECTED_VALUE, adminManager.getParameter(TEST_PARAMETER));
        assertNull(adminManager.getParameter("unknown"));
    }
    
    @Test
    public void testSetParameter() {
        final String newValue = "30";
        
        assertEquals(EXPECTED_VALUE, adminManager.getParameter(TEST_PARAMETER));
        adminManager.setParameter("loanProcessorThreshold", newValue);
        assertEquals(newValue, adminManager.getParameter(TEST_PARAMETER));
    }
    
    @Test
    public void testGetParameters() {
        Map<String, String> parameters = adminManager.getParameters();
        assertNotNull(parameters);
        assertTrue(parameters.size() > 0);
    }
}
