package com.parasoft.parabank.dao.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.parasoft.parabank.dao.AccountDao;
import com.parasoft.parabank.dao.InMemoryAccountDao;
import com.parasoft.parabank.domain.Account;

import static org.junit.Assert.*;

public class InMemoryAccountDaoTest 
{
    private static final int ACCOUNT1_ID = 1;   
    private static final int ACCOUNT2_ID = 2;
    
    private AccountDao accountDao;
    
    
    @Before
    public void setUp() throws Exception {
        List<Account> accounts = new ArrayList<Account>();
        
        Account account = new Account();
        account.setId(ACCOUNT1_ID);
        accounts.add(account);
        
        account = new Account();
        account.setId(ACCOUNT2_ID);
        accounts.add(account);
        
        accountDao = new InMemoryAccountDao(accounts);
    }
    
    @Test
    public void testGetAccount() {
        Account account = accountDao.getAccount(ACCOUNT1_ID);
        assertNotNull(account);
        assertEquals(ACCOUNT1_ID, account.getId());
        
        account = accountDao.getAccount(ACCOUNT2_ID);
        assertNotNull(account);
        assertEquals(ACCOUNT2_ID, account.getId());
                
        assertNull(accountDao.getAccount(-1));
    }
    
    @Test
    public void testCreateAccount() {
        Account originalAccount = new Account();
        int id = accountDao.createAccount(originalAccount);
        assertEquals(3, id);
        Account newAccount = accountDao.getAccount(id);
        assertEquals(originalAccount, newAccount);
    }
    
    @Test
    public void testUpdateAccount() {
        Account account = accountDao.getAccount(ACCOUNT1_ID);
        account.setBalance(new BigDecimal("100.00"));
        accountDao.updateAccount(account);
        account = accountDao.getAccount(ACCOUNT1_ID);
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }
}
