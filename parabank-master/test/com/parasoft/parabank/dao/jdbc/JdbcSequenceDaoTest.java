package com.parasoft.parabank.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.parasoft.parabank.test.util.AbstractParaBankDataSourceTest;
import static org.junit.Assert.*;

public class JdbcSequenceDaoTest extends AbstractParaBankDataSourceTest {   
    private JdbcSequenceDao sequenceDao;
    
    public void setSequenceDao(JdbcSequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }
    
    @Before
    public void before() throws Exception
    {
    	super.setUp();
    }
    
    @Test
    public void testGetNextId() {
        for (int i = 0; i < 10; i++) {
            assertEquals(12434 + i * JdbcSequenceDao.OFFSET, sequenceDao.getNextId("Customer"));
            assertEquals(13566 + i * JdbcSequenceDao.OFFSET, sequenceDao.getNextId("Account"));
            assertEquals(14476 + i * JdbcSequenceDao.OFFSET, sequenceDao.getNextId("Transaction"));
        }
        
        try {
            sequenceDao.getNextId(null);
            fail("did not throw expected DataAccessException");
        } catch (DataAccessException e) { }
        
        try {
            sequenceDao.getNextId("");
            fail("did not throw expected DataAccessException");
        } catch (DataAccessException e) { }
    }
}
