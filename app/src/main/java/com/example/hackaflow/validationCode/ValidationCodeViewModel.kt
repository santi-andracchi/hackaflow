package com.example.hackaflow.validationCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackaflow.R
import com.example.hackaflow.data.CodeValidation
import com.example.hackaflow.data.DataResult
import com.example.hackaflow.data.UIState
import com.example.hackaflow.koin.HackaFlowApp
import com.example.hackaflow.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ValidationCodeViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _validationState = MutableLiveData<UIState<out CodeValidation>>()
    val validationState: LiveData<UIState<out CodeValidation>> = _validationState

    fun validateCode(code: CharSequence) {
        _validationState.postValue(UIState.Loading())

        viewModelScope.launch {
            authRepository.validateCode(code.toString()).collect {
                when(it) {
                    is DataResult.Success -> {
                        _validationState.postValue(UIState.Success(it.data))
                    }
                    is DataResult.ErrorResult -> {
                        _validationState.postValue(UIState.ErrorMessage(HackaFlowApp.getString(R.string.error_incorrect_code)))
                    }
                }

            }
        }
    }
}