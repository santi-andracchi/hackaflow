package com.example.hackaflow.data

sealed class DataResult<T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Error<T>(val error: T) : DataResult<T>()

}
