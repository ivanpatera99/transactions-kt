interface ITransactionService {
    fun putTransaction(transactionId: Long, amount: Double, type: String, parentId: Long?)
    fun getTransactionsForType(type: String)
    fun getTransactionsSum(transactionId: Long): Double
}