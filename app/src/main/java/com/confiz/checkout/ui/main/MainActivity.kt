package com.confiz.checkout.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.confiz.checkout.databinding.ActivityMainBinding
import com.confiz.checkout.ui.CheckoutPaymentGateway


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUI()
    }

    private fun setupUI() {
        binding.checkoutPaymentGateway.setOnClickListener {
            val intent = Intent(this, CheckoutPaymentGateway::class.java)
            startActivity(intent)
        }
    }

}