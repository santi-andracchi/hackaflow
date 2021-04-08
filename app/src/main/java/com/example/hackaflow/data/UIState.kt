package com.example.hackaflow.data

sealed class UIState<T> {

    class Loading<T> : UIState<T>()

    class Error<T>(val data: T? = null) : UIState<T>()

    class Success<T>(val data: T? = null) : UIState<T>()

}