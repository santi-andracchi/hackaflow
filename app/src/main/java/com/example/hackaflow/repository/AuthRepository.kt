package com.example.hackaflow.repository

import com.example.hackaflow.data.CodeValidation
import com.example.hackaflow.data.DataResult
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(username: String, password: String): Flow<DataResult<JsonObject>>

    suspend fun validateCode(code: String): Flow<DataResult<CodeValidation>>

}