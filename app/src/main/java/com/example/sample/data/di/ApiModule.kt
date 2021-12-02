package com.example.sample.data.di

import com.example.sample.data.webservice.ChuckInterceptor
import com.example.sample.data.webservice.LoggingInterceptor
import com.example.sample.data.webservice.RetrofitServiceBuilder
import com.example.sample.util.Constants
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {

    single(named("default")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
//        if (BuildConfig.DEBUG) {
//            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
//        }
        okHttpBuilder.build()
    }

    single(named("default")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("default")),
            baseUrl = Constants.BASE_URL
        )
    }


}