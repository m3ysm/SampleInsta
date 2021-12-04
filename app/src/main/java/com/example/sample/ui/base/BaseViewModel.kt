package com.example.sample.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    var compositeDisposable = CompositeDisposable()

    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}