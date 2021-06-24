package com.confiz.checkout.data.model

data class CardPaymentBody(
    val amount: Float = 500F,
    val currency: String = "USD",
    val source: Source
)

data class Source(
    val type: String = "token",
    val token: String
)
