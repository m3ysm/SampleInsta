package com.example.sample.data.di

import com.example.sample.ui.gettoken.GetTokenViewModel
import com.example.sample.ui.usermedia.UserMediaViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { GetTokenViewModel() }
    single { UserMediaViewModel() }
}