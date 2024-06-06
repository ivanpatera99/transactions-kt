package com.ivanpatera.mendel.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class TransactionServiceTest {
    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setup() {
        transactionService = TransactionService()
        transactionService.putTransaction(4, 120.4, "test", null)
    }

    @Test
    fun `putTransaction with no parent transaction`() {
        transactionService.putTransaction(1, 100.0, "type1", null)
        assertNotNull(transactionService.transactions[1])
    }

    @Test
    fun `putTransaction with parent transaction`() {
        transactionService.putTransaction(2, 200.0, "type2", 4)
        assertNotNull(transactionService.transactions[2])
        assertEquals(1, transactionService.transactions[4]?.children?.size)
    }

    @Test 
    fun `putTransaction with non-existent parent transaction`() {
        assertThrows<IllegalArgumentException> {
            transactionService.putTransaction(3, 300.0, "type3", 5)
        }
    }

    @Test
    fun `getTransactionsForType with no transactions should return empty list`() {
        assertEquals(0, transactionService.getTransactionsForType("type").size)
    }

    @Test
    fun `getTransactionsForType with n transactions should return a full list`() {
        assertEquals(1, transactionService.getTransactionsForType("test").size)
    }

    @Test
    fun `getTransactionsSum for a transaction with no children`(){
        assertEquals(120.4, transactionService.getTransactionsSum(4))
    }

    @Test
    fun `getTransactionsSum for a transaction with children`(){
         transactionService.putTransaction(2, 200.0, "type2", 4)
         transactionService.putTransaction(3, 300.0, "type3", 2)
         assertEquals(620.4, transactionService.getTransactionsSum(4))
         assertEquals(500.0, transactionService.getTransactionsSum(2))
    }

    @Test 
    fun `getTransactionsSum for a transaction with nested children`(){
        transactionService.putTransaction(10, 200.0, "type2", 4)
        transactionService.putTransaction(11, 300.0, "type3", 4)
        transactionService.putTransaction(12, 500.0, "type5", 11)
        assertEquals(1120.4, transactionService.getTransactionsSum(4))
        assertEquals(800.0, transactionService.getTransactionsSum(11))
        assertEquals(500.0, transactionService.getTransactionsSum(12))
    }

    @Test
    fun `getTransactionsSum for a non-existent transaction`(){
        assertThrows<IllegalArgumentException> {
            transactionService.getTransactionsSum(5)
        }
    }
}