package com.example.sample.data.di

import com.example.sample.util.CustomWebView
import org.koin.dsl.module

val utilModule = module {
    single { CustomWebView() }
}