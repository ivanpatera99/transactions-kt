package com.ivanpatera.mendel.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class TransactionRequestBodyDTO(
    @field:NotBlank(message = "AMOUNT_IS_REQUIRED")
    @field:Positive(message = "AMOUNT_MUST_BE_POSITIVE")
    val amount: Double,

    @field:NotBlank(message = "Type is required")
    val type: String,

    @field:Positive(message = "PARENT_ID_MUST_BE_POSITIVE")
    val parentId: Long?
)