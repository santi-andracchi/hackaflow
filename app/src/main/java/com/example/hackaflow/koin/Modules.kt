package com.example.hackaflow.koin

import com.example.hackaflow.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val appModules = module {
    viewModel { BaseViewModel() }
    }

