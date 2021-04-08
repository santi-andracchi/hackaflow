package com.example.hackaflow.repository

import com.example.hackaflow.data.DataResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(username: String, password: String): Flow<DataResult<out Any>>

}