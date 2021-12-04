package com.example.sample.data.di

import com.example.sample.data.service.StorageService
import com.example.sample.util.CustomWebView
import org.koin.dsl.module

val utilModule = module {
    single { CustomWebView() }
}

val StorageServiceModule = module {
    single { StorageService(get()) }
}