package com.example.sample.data.di

import com.example.sample.ui.gettoken.domain.GetAccessTokenUseCase
import com.example.sample.ui.usermedia.domain.GetMediaDataUseCase
import com.example.sample.ui.usermedia.domain.GetUserMediaIdAndCaptionUseCase
import com.example.sample.ui.usermedia.domain.GetUserNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetAccessTokenUseCase(get()) }
    single { GetUserNameUseCase(get()) }
    single { GetUserMediaIdAndCaptionUseCase(get()) }
    single { GetMediaDataUseCase(get()) }
}