package com.example.hackaflow.extensions
import com.example.hackaflow.data.DataResult
import retrofit2.Response

fun <T, R> Response<T>.parse(parseMethod: (T) -> R): DataResult<R> {
    if (isSuccessful) {
        body()?.let {
            return DataResult.Success(parseMethod(it))
        }
    }
    return DataResult.ErrorResult(Throwable("Internal server error"))
}
