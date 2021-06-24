package com.confiz.checkout.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.confiz.checkout.data.api.ApiHelper
import com.confiz.checkout.data.repository.MainRepository
import com.confiz.checkout.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(MainRepository(apiHelper)) as T
        throw IllegalArgumentException("Unknown class name")
    }
}