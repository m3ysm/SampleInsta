package com.example.sample.ui.usermedia

import androidx.lifecycle.LiveData
import com.example.sample.data.model.*
import com.example.sample.ui.base.BaseViewModel
import com.example.sample.ui.usermedia.domain.GetMediaDataUseCase
import com.example.sample.ui.usermedia.domain.GetUserMediaIdAndCaptionUseCase
import com.example.sample.ui.usermedia.domain.GetUserNameUseCase
import com.example.sample.util.SingleLiveEvent
import org.koin.core.inject

class UserMediaViewModel : BaseViewModel() {

    private val getUserNameUseCase: GetUserNameUseCase by inject()
    private val getUserMediaIdAndCaptionUseCase: GetUserMediaIdAndCaptionUseCase by inject()
    private val getMediaDataUseCase: GetMediaDataUseCase by inject()

    private val _getUserNameLiveData = SingleLiveEvent<MyResponse<GetUserNameResponseModel>>()
    val getUserNameLiveData: LiveData<MyResponse<GetUserNameResponseModel>>
        get() = _getUserNameLiveData

    private val _getUserMediaIdAndCaptionLiveData =
        SingleLiveEvent<MyResponse<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>>()
    val getUserMediaIdAndCaptionLiveData: LiveData<MyResponse<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>>
        get() = _getUserMediaIdAndCaptionLiveData

    private val _getMediaDataLiveData = SingleLiveEvent<MyResponse<GetMediaDataResponseModel>>()
    val getMediaDataLiveData: LiveData<MyResponse<GetMediaDataResponseModel>>
        get() = _getMediaDataLiveData
}