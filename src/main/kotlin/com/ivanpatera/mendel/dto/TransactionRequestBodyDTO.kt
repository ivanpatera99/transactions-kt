package com.ivanpatera.mendel.dto


data class TransactionRequestBodyDTO(
    val amount: Double,
    val type: String,
    val parentId: Long?
)