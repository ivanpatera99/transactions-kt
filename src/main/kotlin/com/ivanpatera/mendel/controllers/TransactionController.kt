package com.ivanpatera.mendel.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired
import com.ivanpatera.mendel.services.TransactionService
import com.ivanpatera.mendel.dto.TransactionRequestBodyDTO
import com.ivanpatera.mendel.dto.TransactionResponseDTO

@RestController()
@RequestMapping("/transactions")
class TransactionController @Autowired constructor(private val transactionService: TransactionService){

    @PutMapping("/{transactionId}")
    fun putTransaction(@PathVariable transactionId: Long, @RequestBody transactionContent: TransactionRequestBodyDTO): ResponseEntity<Any> {
        if (transactionContent.amount == 0.0 || transactionContent.type == null) {
                return ResponseEntity.badRequest().body(mapOf("error" to "Invalid request body"))
        }
        try {
            transactionService.putTransaction(transactionId, transactionContent.amount, transactionContent.type, transactionContent.parentId)
            return ResponseEntity.ok().body(TransactionResponseDTO("transaction saved successfully"))
        } catch (e: Exception) {
            if (e.message == "PARENT_TXN_NOT_FOUND") {
                return ResponseEntity.status(404).body(mapOf("error" to "PARENT_TXN_NOT_FOUND"))
            } else if (e.message == "TXN_ALREADY_EXISTS") {
                return ResponseEntity.status(409).body(mapOf("error" to "TXN_ALREADY_EXISTS"))
            } else {
                return ResponseEntity.internalServerError().body(mapOf("error" to "INTERNAL_SERVER_ERROR"))
            }
        }
    }
    
}