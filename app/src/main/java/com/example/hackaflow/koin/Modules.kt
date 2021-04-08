package com.example.hackaflow.koin

import com.example.hackaflow.base.BaseViewModel
import com.example.hackaflow.validationCode.ValidationCodeViewModel
import com.example.hackaflow.validationCode.ValidationCodeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@ExperimentalCoroutinesApi
val appModules = module {
    single {
        provideRetrofit(get(), "https://flow.com.ar")
    }
    factory { providesOkHttpClient(get()) }


    viewModel { BaseViewModel() }
    viewModel { ValidationCodeViewModel() }
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

