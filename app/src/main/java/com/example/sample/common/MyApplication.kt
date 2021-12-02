package com.example.sample.common

import androidx.multidex.MultiDexApplication
import com.example.sample.data.di.apiModule
import com.example.sample.data.di.repositoryModule
import com.example.sample.data.di.useCaseModule
import com.example.sample.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    apiModule,
                    useCaseModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}