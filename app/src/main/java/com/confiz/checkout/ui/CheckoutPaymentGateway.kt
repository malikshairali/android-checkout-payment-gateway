package com.confiz.checkout.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Visibility
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import com.confiz.checkout.BuildConfig
import com.confiz.checkout.data.api.ApiHelper
import com.confiz.checkout.data.api.RetrofitBuilder
import com.confiz.checkout.databinding.ActivityCheckoutPaymentGatewayBinding
import com.confiz.checkout.ui.base.ViewModelFactory
import com.confiz.checkout.ui.main.MainViewModel
import com.confiz.checkout.utils.Status

class CheckoutPaymentGateway : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutPaymentGatewayBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutPaymentGatewayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViewModel()
        setupPaymentForm()
    }

    private fun makePayment(token: String) {
        viewModel.makePayment(BuildConfig.SECRET_KEY,
            token).observe(this, Observer {
            it?.let { resource ->
                hideProgressBar()
                when (resource.status) {
                    Status.SUCCESS -> {
                        showToast("Payment status: ${resource.data?.status}")
                    }
                    Status.ERROR -> {
                        showToast(resource.message)
                    }
                    Status.LOADING -> {
                        // Todo: I guess it's not needed, will refactor later
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    private fun setupPaymentForm() {
        val mFormListener: PaymentForm.PaymentFormCallback = object :
            PaymentForm.PaymentFormCallback {
            override fun onFormSubmit() {
                showProgressBar()
            }

            override fun onTokenGenerated(response: CardTokenisationResponse) {
                binding.checkoutCardForm.clearForm()
                makePayment(response.token)
                showToast("Token generated successfully: ${response.token}")
            }

            override fun onError(response: CardTokenisationFail) {
                hideProgressBar()
                showToast("Error: ${response.errorType}")
            }

            override fun onNetworkError(error: NetworkError) {
                hideProgressBar()
                showToast("Please check your internet connection.")
            }

            override fun onBackPressed() {
                finish()
            }
        }

        // initialise the payment from
        binding.checkoutCardForm
            .setFormListener(mFormListener)        // set the callback
            .setEnvironment(Environment.SANDBOX)   // set the environemnt
            .setKey(BuildConfig.PUBLIC_KEY);       // set your public key
    }

    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun hideProgressBar() {
        binding.progressbar.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressbar.isVisible = true
    }
}