package com.confiz.checkout.data.api

import com.confiz.checkout.data.model.CardPaymentBody

class ApiHelper(private val apiService: ApiService) {
    suspend fun makePayment(secretKey: String, body: CardPaymentBody) =
        apiService.makePayment(secretKey = secretKey, body = body)
}