package com.example.sample.ui.usermedia.domain

import com.example.sample.data.model.MyResponse
import com.example.sample.data.service.StorageService
import io.reactivex.Single

class CheckWriteExternalStoragePermissionUseCase(private val storageService: StorageService) {
    fun invoke(): Single<MyResponse<Boolean>?> {
        return Single.just(MyResponse.success(storageService.isWriteExternalStoragePermissionGranted()))
    }
}