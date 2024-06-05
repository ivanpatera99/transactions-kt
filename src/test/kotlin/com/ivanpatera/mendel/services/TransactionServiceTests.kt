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

   
}