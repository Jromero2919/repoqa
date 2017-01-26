package com.parasoft.parabank.service;

import org.junit.Test;

import com.parasoft.parabank.test.util.AbstractAdminOperationsTest;

@SuppressWarnings("deprecation")
public class ParaBankAdminServiceImplTest extends AbstractAdminOperationsTest {
    private ParaBankService paraBankService;

    public void setParaBankService(ParaBankService paraBankService) {
        this.paraBankService = paraBankService;
    }
    
    @Test
    public void before() throws Exception
    {
    	setUp();
    }
    
    @Test
    public void testInitializeDB() throws Exception {
        assertDBInitialized(new DBInitializer() {
            public void initializeDB() throws Exception {
                paraBankService.initializeDB();
            }
        });
    }

    @Test
    public void testCleanDB() throws Exception {
        assertDBClean(new DBCleaner() {
            public void cleanDB() throws Exception {
                paraBankService.cleanDB();
            }
        });
    }
    
    @Test
    public void testStartupJmsListener() {
        assertJmsStartup(new JmsStartupManager() {
            public void startupJmsListener() {
                paraBankService.startupJmsListener();
            }
        });
    }
    
    @Test
    public void testShutdownJmsListener() {
        assertJmsShutdown(new JmsShutdownManager() {           
            public void shutdownJmsListener() {
                paraBankService.shutdownJmsListener();
            }
        });
    }
    
    @Test
    public void testSetParameter() {
        final String PARAM_NAME = "loanProvider";
        final String PARAM_VALUE = "test";
        final String SQL = "SELECT value FROM Parameter WHERE name = ?";
        
        assertEquals("ws", getJdbcTemplate().queryForObject(SQL, String.class, PARAM_NAME));
        paraBankService.setParameter(PARAM_NAME, PARAM_VALUE);
        assertEquals(PARAM_VALUE, getJdbcTemplate().queryForObject(SQL, String.class, PARAM_NAME));
    }
}
