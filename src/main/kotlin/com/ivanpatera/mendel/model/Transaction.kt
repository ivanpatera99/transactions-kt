package com.ivanpatera.mendel.model

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: String,
    val children: MutableList<Long> = mutableListOf()
)