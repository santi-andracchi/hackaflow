package com.example.hackaflow.koin

import com.example.hackaflow.data.CodeValidation
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FlowAPI {

    @POST("/hackaflow/v1/code-validate")
    suspend fun validateCode(@Body requestData: JsonObject): Response<CodeValidation>

    @POST("/api/login")
    suspend fun login(@Body requestData: JsonObject): Response<JsonObject>
}