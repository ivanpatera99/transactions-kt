package com.ivanpatera.mendel.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ivanpatera.mendel.dto.TransactionRequestBodyDTO
import com.ivanpatera.mendel.services.TransactionService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.junit.jupiter.api.BeforeEach

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setUp() {
        Mockito.`when`(transactionService.putTransaction(1, 100.0, "type", null)).then { }
    }

    @Test
    fun `putTransaction should return 200 when transaction is saved successfully`() {
        val transactionId = 1L
        val transactionRequestBodyDTO = TransactionRequestBodyDTO(100.0, "type", null)

        Mockito.doNothing().`when`(transactionService).putTransaction(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyString(), Mockito.any())

        mockMvc.perform(put("/transactions/$transactionId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestBodyDTO)))
            .andExpect(status().isOk)
    }

    @Test
    fun `putTransaction should return 400 when request body is invalid`() {
        val transactionId = 1L
        val transactionRequestBodyDTO = TransactionRequestBodyDTO(0.0, "null", null)

        Mockito.doNothing().`when`(transactionService).putTransaction(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyString(), Mockito.any())

        mockMvc.perform(put("/transactions/$transactionId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestBodyDTO)))
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{"error":"Invalid request body"}"""))
    }

    @Test
    fun `putTransaction should return 404 when parent transaction is not found`() {
        val transactionId = 2L
        val transactionRequestBodyDTO = TransactionRequestBodyDTO(100.0, "type", 100)

         Mockito.`when`(transactionService.putTransaction(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyString(), Mockito.any()))
        .thenThrow(IllegalArgumentException("PARENT_TXN_NOT_FOUND"))

        mockMvc.perform(put("/transactions/$transactionId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestBodyDTO)))
            .andExpect(status().isNotFound)
            .andExpect(content().json("""{"error":"PARENT_TXN_NOT_FOUND"}"""))
    }

    @Test
    fun `putTransaction should return 409 when transaction already exists`() {
        val transactionId = 11L
        val transactionRequestBodyDTO = TransactionRequestBodyDTO(100.0, "type", null)

        Mockito.`when`(transactionService.putTransaction(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyString(), Mockito.any()))
        .thenThrow(IllegalArgumentException("TXN_ALREADY_EXISTS"))

        mockMvc.perform(put("/transactions/$transactionId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestBodyDTO)))
            .andExpect(status().isConflict())
            .andExpect(content().json("""{"error":"TXN_ALREADY_EXISTS"}"""))
    }

    @Test
    fun `getTransactionsForType should return an empty list when no transactions are found`() {
        val type = "type"

        Mockito.`when`(transactionService.getTransactionsForType(type)).thenReturn(mutableListOf())

        mockMvc.perform(get("/transactions/types/$type"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"transactions":[]}"""))
    }

    @Test
    fun `getTransactionsForType should return a list of transactions when transactions are found`() {
        val type = "type"
        val transactions = mutableListOf(1L, 2L, 3L)

        Mockito.`when`(transactionService.getTransactionsForType(type)).thenReturn(transactions)

        mockMvc.perform(get("/transactions/types/$type"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"transactions":[1,2,3]}"""))
    }

    @Test
    fun `getTransactionsSum should return 404 when transaction is not found`() {
        val transactionId = 1L

        Mockito.`when`(transactionService.getTransactionsSum(transactionId)).thenThrow(IllegalArgumentException("TXN_NOT_FOUND"))

        mockMvc.perform(get("/transactions/sum/$transactionId"))
            .andExpect(status().isNotFound)
            .andExpect(content().json("""{"error":"TXN_NOT_FOUND"}"""))
    }

    @Test
    fun `getTransactionsSum should return the sum of transactions when transaction is found`() {
        val transactionId = 1L
        val sum = 100.0

        Mockito.`when`(transactionService.getTransactionsSum(transactionId)).thenReturn(sum)

        mockMvc.perform(get("/transactions/sum/$transactionId"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"sum":100.0}"""))
    }

}