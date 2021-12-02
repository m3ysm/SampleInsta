package com.example.sample.data.di

import com.example.sample.data.webservice.ChuckInterceptor
import com.example.sample.data.webservice.LoggingInterceptor
import com.example.sample.data.webservice.RetrofitServiceBuilder
import com.example.sample.util.Constants
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {

    single(named("basicDisplay")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
        }
        okHttpBuilder.build()
    }

    single(named("basicDisplay")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("basicDisplay")),
            baseUrl = Constants.INSTAGRAM_BASIC_DISPLAY_BASE_URL
        )
    }

    single(named("graph")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
        }
        okHttpBuilder.build()
    }

    single(named("graph")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("graph")),
            baseUrl = Constants.INSTAGRAM_GRAPH_BASE_URL
        )
    }
}