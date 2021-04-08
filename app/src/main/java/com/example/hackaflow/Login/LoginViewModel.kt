package com.example.hackaflow.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackaflow.data.DataResult
import com.example.hackaflow.data.UIState
import com.example.hackaflow.repository.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableLiveData<UIState<out Any>>()
    val loginState: LiveData<UIState<out Any>> = _loginState

    fun login(userName: String, password: String) {
        _loginState.postValue(UIState.Loading())
        viewModelScope.launch(IO) {
            authRepository.login(userName, password).collect() { tokenInfoResponse ->
                when (tokenInfoResponse) {
                   is DataResult.Success -> {
                       _loginState.postValue(UIState.Success())
                   }
                   is DataResult.Error -> {
                       _loginState.postValue(UIState.Error())
                   }
                }
            }
        }
    }
}