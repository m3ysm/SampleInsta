package com.example.sample.data.webservice

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor

object ChuckInterceptor {

    fun getInstance(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }
}