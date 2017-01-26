package com.parasoft.parabank.dao.jdbc;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.parasoft.parabank.dao.AdminDao;
import com.parasoft.parabank.domain.Account;
import com.parasoft.parabank.test.util.AbstractAdminOperationsTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JdbcAdminDaoTest extends AbstractAdminOperationsTest {
    private static final String TEST_PARAMETER = "loanProcessorThreshold";
    private static final String EXPECTED_VALUE = "20";
    
    private AdminDao adminDao;
    
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }
}
