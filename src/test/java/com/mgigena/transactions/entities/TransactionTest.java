package com.mgigena.transactions.entities;




import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class TransactionTest {
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        transaction = new Transaction();
    }

    @Test
    public void testSetAndGetType() {
        String type = "type1";
        assertNull(transaction.getType());
        transaction.setType(type);
        assertEquals(type, transaction.getType());
    }

    @Test
    public void testSetAndGetAmount() {
        double amount = 100.00;
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount(),0);
    }

    @Test
    public void testSetAndGetParentId() {
        Long parent_id = 2L;
        transaction.setParent_id(2L);
        assertEquals(parent_id, transaction.getParent_id());
    }
}
