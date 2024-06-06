package com.ivanpatera.mendel.services

import org.springframework.stereotype.Service
import com.ivanpatera.mendel.model.Transaction


@Service
class TransactionService: ITransactionService {
    val transactions = mutableMapOf<Long, Transaction>()

    override fun putTransaction(transactionId: Long, amount: Double, type: String, parentId: Long?) {
        if (transactions.containsKey(transactionId)) {
            throw IllegalArgumentException("TXN_ALREADY_EXISTS")
        }
        val newTransaction = Transaction(transactionId, amount, type)
        parentId?.let { 
            if (!transactions.containsKey(it)) { 
                throw IllegalArgumentException("PARENT_TXN_NOT_FOUND")
            }
            transactions[it]?.children?.add(transactionId)
        }
        transactions[transactionId] = newTransaction
    }

    override fun getTransactionsForType(type: String): MutableList<Long> {
        return transactions.filter { it.value.type == type }.keys.toMutableList()
    }

    override fun getTransactionsSum(transactionId: Long): Double {
        return transactions[transactionId]?.let {
            it.amount + it.children.map { getTransactionsSum(it) }.sum()
        } ?: throw IllegalArgumentException("TXN_NOT_FOUND")
    }
}