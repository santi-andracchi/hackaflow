package com.example.hackaflow.koin

import com.example.hackaflow.base.BaseViewModel
import com.example.hackaflow.validationCode.ValidationCodeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { BaseViewModel() }
    viewModel { ValidationCodeViewModel() }
}

