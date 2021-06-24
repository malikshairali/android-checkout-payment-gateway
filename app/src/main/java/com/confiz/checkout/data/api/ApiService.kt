package com.confiz.checkout.data.api

import com.confiz.checkout.data.model.CardPaymentBody
import com.confiz.checkout.data.retrofit.CardPaymentResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/payments")
    suspend fun makePayment(
        @Header("Authorization") secretKey: String,
        @Body body: CardPaymentBody
    ): CardPaymentResponse
}