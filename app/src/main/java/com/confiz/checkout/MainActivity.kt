package com.confiz.checkout

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated
import com.checkout.android_sdk.Models.BillingModel
import com.checkout.android_sdk.Models.PhoneModel
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.PaymentForm.PaymentFormCallback
import com.checkout.android_sdk.Request.CardTokenisationRequest
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import kotlinx.android.synthetic.main.activity_main.*
import javax.crypto.Cipher.PUBLIC_KEY


class MainActivity : AppCompatActivity() {
    private lateinit var mCheckoutAPIClient: CheckoutAPIClient // include the module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFormListener: PaymentFormCallback = object : PaymentFormCallback {
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
            }
        }

        // initialise the payment from
        checkout_card_form
            .setFormListener(mFormListener)        // set the callback
            .setEnvironment(Environment.SANDBOX)   // set the environemnt
            .setKey(BuildConfig.PUBLIC_KEY);                     // set your public key

        val mTokenListener: OnTokenGenerated = object : OnTokenGenerated {
            override fun onTokenGenerated(token: CardTokenisationResponse) {
                // your token
            }

            override fun onError(error: CardTokenisationFail) {
                // your error
            }

            override fun onNetworkError(error: NetworkError) {
                // your network error
            }
        }

        mCheckoutAPIClient = CheckoutAPIClient(
            this,  // context
            BuildConfig.PUBLIC_KEY,  // your public key
            Environment.SANDBOX // the environment
        )
        mCheckoutAPIClient.setTokenListener(mTokenListener) // pass the callback

        // Pass the paylod and generate the token
        mCheckoutAPIClient.generateToken(
            CardTokenisationRequest(
                "4242424242424242",
                "name",
                "06",
                "25",
                "100",
                BillingModel(
                    "address line 1",
                    "address line 2",
                    "postcode",
                    "UK",
                    "city",
                    "state"
                ),
                PhoneModel(
                    "+44",
                    "07123456789"
                )
            )
        );

    }
}