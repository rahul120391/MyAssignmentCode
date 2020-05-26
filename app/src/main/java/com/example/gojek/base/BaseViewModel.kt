package com.example.gojek.base

import androidx.lifecycle.ViewModel
import com.example.gojek.networking.cleanarchitecturebase.UseCaseHandler

open class BaseViewModel(val useCaseHandler: UseCaseHandler?):ViewModel() {
}