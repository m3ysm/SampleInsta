package com.example.sample.data.di

import com.example.sample.data.repository.GetAccessTokenRepository
import com.example.sample.data.repository.UserMediaRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GetAccessTokenRepository(get()) }
    single { UserMediaRepository(get()) }
}