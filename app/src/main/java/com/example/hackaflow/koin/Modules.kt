package com.example.hackaflow.koin

import com.example.hackaflow.BuildConfig
import com.example.hackaflow.Login.LoginViewModel
import com.example.hackaflow.base.BaseViewModel
import com.example.hackaflow.repository.AuthRepository
import com.example.hackaflow.repository.AuthRepositoryImpl
import com.example.hackaflow.validationCode.ValidationCodeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModules = module {
    single { provideRetrofit(get(), "https://hackaflow-team3-backend.herokuapp.com/") }
    single { provideAuthRepository(get()) }

    factory { providesHttplogging() }
    factory { providesOkHttpClient(get()) }
    factory { createWebService<FlowAPI>(get()) }

    viewModel { BaseViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { ValidationCodeViewModel() }
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun providesHttplogging(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return interceptor
}

fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

inline fun <reified T> createWebService(retrofit: Retrofit): T = retrofit.create(T::class.java)
fun provideAuthRepository(flowApi: FlowAPI): AuthRepository = AuthRepositoryImpl(flowApi)