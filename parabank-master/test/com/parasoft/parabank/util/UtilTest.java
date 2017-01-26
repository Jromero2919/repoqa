package com.parasoft.parabank.util;

import org.junit.Assert;
import org.junit.Test;

import com.parasoft.parabank.util.Util;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class UtilTest {
	
	@Test
    public void testEquals() {
        assertTrue(Util.equals(null, null));
        assertFalse(Util.equals("foo", null));
        assertFalse(Util.equals(null, "bar"));
        assertFalse(Util.equals("foo", "bar"));
        assertTrue(Util.equals("foo", "foo"));
    }

	@Test
    public void testEmpty() {
        assertTrue(Util.isEmpty(null));
        assertTrue(Util.isEmpty(""));
        assertFalse(Util.isEmpty("s"));
    }
}
