package com.example.hackaflow.validationCode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ValidationCodeViewModel: ViewModel() {

    fun validateCode(code: CharSequence) {
        viewModelScope.launch {
            //post code and wait for result.
        }
    }
}