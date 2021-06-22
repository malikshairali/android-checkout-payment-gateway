package com.confiz.checkout

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import com.confiz.checkout.databinding.ActivityCheckoutPaymentGatewayBinding

class CheckoutPaymentGateway : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutPaymentGatewayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutPaymentGatewayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializePaymentForm()
    }


    private fun initializePaymentForm() {
        val mFormListener: PaymentForm.PaymentFormCallback = object :
            PaymentForm.PaymentFormCallback {
            override fun onFormSubmit() {
                // form submit initiated; you can potentially display a loader
            }

            override fun onTokenGenerated(response: CardTokenisationResponse) {
                // your token is here: response.token
                Log.d("Token: ", response.token)
                Toast.makeText(applicationContext, response.token, Toast.LENGTH_LONG).show()
//                checkout_card_form.clearForm() // this clears the Payment Form
            }

            override fun onError(response: CardTokenisationFail) {
                // token request error
            }

            override fun onNetworkError(error: NetworkError) {
                // network error
            }

            override fun onBackPressed() {
                // the user decided to leave the payment page
//                checkout_card_form.clearForm() // this clears the Payment Form
                finish()
            }
        }

        // initialise the payment from
        binding.checkoutCardForm
            .setFormListener(mFormListener)        // set the callback
            .setEnvironment(Environment.SANDBOX)   // set the environemnt
            .setKey(BuildConfig.PUBLIC_KEY);       // set your public key
    }
}