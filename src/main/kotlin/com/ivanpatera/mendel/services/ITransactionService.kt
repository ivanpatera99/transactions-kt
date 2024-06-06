package com.ivanpatera.mendel.services

interface ITransactionService {
    /**
    * Adds a transaction to the service.
    * @param transactionId The ID of the transaction.
    * @param amount The amount of the transaction.
    * @param type The type of the transaction.
    * @param parentId The ID of the parent transaction.
    * @throws IllegalArgumentException If the transaction already exists or the parent transaction does not exist.
    */
    fun putTransaction(transactionId: Long, amount: Double, type: String, parentId: Long?)

    /**
    * Returns a list of transaction IDs for a given type.
    * @param type The type of the transactions.
    * @return A list of transaction IDs.
    */
    fun getTransactionsForType(type: String): MutableList<Long>

    /**
    * Returns the sum of the transaction and its children.
    * @param transactionId The ID of the transaction.
    * @return The sum of the transaction and its children.
    * @throws IllegalArgumentException If the transaction does not exist.
    */
    fun getTransactionsSum(transactionId: Long): Double
}