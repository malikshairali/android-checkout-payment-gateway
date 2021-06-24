package com.confiz.checkout.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.confiz.checkout.data.model.CardPaymentBody
import com.confiz.checkout.data.model.Source
import com.confiz.checkout.data.repository.MainRepository
import com.confiz.checkout.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun makePayment(secretKey: String, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.makePayment(
                        secretKey,
                        CardPaymentBody(source = Source(token = token))
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}