package com.example.sample.data.di

import com.example.sample.data.webservice.*
import com.example.sample.util.Constants
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {

    single(named("default")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("default")),
            baseUrl = Constants.INSTAGRAM_BASIC_DISPLAY_BASE_URL
        )
    }

    single(named("graph")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("graph")),
            baseUrl = Constants.INSTAGRAM_GRAPH_BASE_URL
        )
    }

    single {
        RetrofitServiceBuilder.buildService(
            retrofit = get(named("default")),
            service = InstagramBasicDisplayAPI::class.java
        )
    }

    single {
        RetrofitServiceBuilder.buildService(
            retrofit = get(named("graph")),
            service = InstagramGraphApi::class.java
        )
    }

    single(named("default")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
        }
        okHttpBuilder.build()
    }

    single(named("graph")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
        }
        okHttpBuilder.build()
    }
}