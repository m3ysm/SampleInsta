package com.example.sample.data.webservice

import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {

    fun getInstance(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}