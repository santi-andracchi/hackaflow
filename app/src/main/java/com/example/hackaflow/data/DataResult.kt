package com.example.hackaflow.data

import android.util.Log

sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Error<T>(val error: T) : DataResult<T>()

    data class ErrorResult(val error: Throwable) : DataResult<Nothing>() {
        fun getMessage() = error.message?.let { it } ?: ""
    }

}
