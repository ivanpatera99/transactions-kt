package com.ivanpatera.mendel.dto

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.DecimalMin


data class TransactionRequestBodyDTO(
    val amount: Double,
    val type: String,
    val parentId: Long?
)