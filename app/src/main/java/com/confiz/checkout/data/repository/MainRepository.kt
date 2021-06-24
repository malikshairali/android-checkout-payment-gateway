package com.confiz.checkout.data.repository

import com.confiz.checkout.data.api.ApiHelper
import com.confiz.checkout.data.model.CardPaymentBody

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun makePayment(secretKey: String, body: CardPaymentBody) =
        apiHelper.makePayment(secretKey, body)
}