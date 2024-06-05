import org.springframework.stereotype.Service


@Service
class TransactionService: ITransactionService {
    val transactions = mutableMapOf<Long, Transaction>()

    override fun putTransaction(transactionId: Long, amount: Double, type: String, parentId: Long?) {
        val newTransaction = Transaction(transactionId, amount, type)
        parentId?.let { 
            if (!transactions.containsKey(it)) { 
                throw IllegalArgumentException("PARENT_TXN_NOT_FOUND")
            }
            transactions[it]?.children?.add(transactionId)
        }
        transactions[transactionId] = newTransaction
    }

    override fun getTransactionsForType(type: String) {
        println("Type: $type")
    }

    override fun getTransactionsSum(transactionId: Long): Double {
        println("Transaction ID: $transactionId")
        return 0.0
    }
}