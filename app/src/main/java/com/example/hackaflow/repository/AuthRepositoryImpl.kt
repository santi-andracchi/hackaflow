package com.example.hackaflow.repository

import com.example.hackaflow.data.CodeValidation
import com.example.hackaflow.data.DataResult
import com.example.hackaflow.extensions.parse
import com.example.hackaflow.koin.FlowAPI
import com.google.gson.JsonObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class AuthRepositoryImpl(private val flowApi: FlowAPI) : AuthRepository {


    override suspend fun login(username: String, password: String): Flow<DataResult<JsonObject>> {
        val data = JsonObject().apply {
            addProperty("username", username)
            addProperty("password", password)
        }

        return flow {
            emit(flowApi.login(data))
        }.map { res ->
            res.parse { it }
        }.catch {
            emit(DataResult.ErrorResult(Throwable("Internal server error")))
        }
    }

    override suspend fun validateCode(code: String): Flow<DataResult<CodeValidation>> {
        val data = JsonObject().apply {
            addProperty("code", code)
        }

        return flow {
            emit(flowApi.validateCode(data))
        }.map { res ->
            res.parse { it }
        }.catch { emit(DataResult.ErrorResult(Throwable("Internal server error"))) }
    }
}

