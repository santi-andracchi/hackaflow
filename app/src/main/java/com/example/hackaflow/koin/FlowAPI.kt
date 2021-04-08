package com.example.hackaflow.koin

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FlowAPI {

    @POST("/api/validateCode")
    suspend fun validateCode(@Body requestData: JsonObject): Response<JsonObject>

    @POST("/api/login")
    suspend fun login(@Body requestData: JsonObject): Response<JsonObject>
}