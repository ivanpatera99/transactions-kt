package com.ivanpatera.mendel.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Valid
import com.ivanpatera.mendel.services.TransactionService
import com.ivanpatera.mendel.dto.TransactionRequestBodyDTO
import com.ivanpatera.mendel.dto.TransactionResponseDTO

@RestController()
@RequestMapping("/transactions")
class TransactionController @Autowired constructor(private val transactionService: TransactionService){

    @PutMapping("/{transactionId}")
    fun putTransaction(@PathVariable transactionId: Long, @RequestBody @Valid transactionContent: TransactionRequestBodyDTO): TransactionResponseDTO {
        transactionService.putTransaction(transactionId, transactionContent.amount, transactionContent.type, transactionContent.parentId)
        return TransactionResponseDTO("Transaction added successfully")
    }
}