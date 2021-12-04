package com.example.sample.ui.usermedia

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.*
import com.example.sample.ui.base.BaseViewModel
import com.example.sample.ui.usermedia.domain.CheckWriteExternalStoragePermissionUseCase
import com.example.sample.ui.usermedia.domain.GetMediaDataUseCase
import com.example.sample.ui.usermedia.domain.GetUserMediaIdAndCaptionUseCase
import com.example.sample.ui.usermedia.domain.GetUserNameUseCase
import com.example.sample.util.SingleLiveEvent
import com.example.sample.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserMediaViewModel : BaseViewModel() {

    private val getUserNameUseCase: GetUserNameUseCase by inject()
    private val getUserMediaIdAndCaptionUseCase: GetUserMediaIdAndCaptionUseCase by inject()
    private val getMediaDataUseCase: GetMediaDataUseCase by inject()
    private val checkWriteExternalStoragePermissionUseCase: CheckWriteExternalStoragePermissionUseCase by inject()

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

    private val _downloadLiveData = SingleLiveEvent<MyResponse<DownloadManager.Request>>()
    val downloadLiveData: LiveData<MyResponse<DownloadManager.Request>>
        get() = _downloadLiveData

    private val _writeExternalStoragePermissionLiveData = SingleLiveEvent<MyResponse<Boolean>>()
    val writeExternalStoragePermissionLiveData: LiveData<MyResponse<Boolean>>
        get() = _writeExternalStoragePermissionLiveData

    val medias = ArrayList<Media>()
    var accessToken = ""
    var userId = 0L

    fun getUserName(userId: String, accessToken: String) {
        _getUserNameLiveData.value = MyResponse.loading()
        sendGetUserNameRequest(userId, accessToken)
    }

    private fun sendGetUserNameRequest(userId: String, accessToken: String) {
        compositeDisposable += getUserNameUseCase.invoke(userId, accessToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _getUserNameLiveData.value = it
            }, {
                _getUserNameLiveData.value = MyResponse.failed(NetworkException())
            })
    }

    fun getUserMediaIdAndCaption(userId: String, accessToken: String) {
        _getUserMediaIdAndCaptionLiveData.value = MyResponse.loading()
        sendGetUserMediaIdAndCaptionRequest(userId, accessToken)
    }

    private fun sendGetUserMediaIdAndCaptionRequest(userId: String, accessToken: String) {
        compositeDisposable += getUserMediaIdAndCaptionUseCase.invoke(userId, accessToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val response = it.data
                val data = response?.data
                data?.forEach { responseModel ->
                    addMediaToMediaArray(responseModel)
                    getMediaData(responseModel.id, accessToken)

                }
                _getUserMediaIdAndCaptionLiveData.value = it
            }, {
                _getUserMediaIdAndCaptionLiveData.value = MyResponse.failed(NetworkException())
            })
    }

    private fun addMediaToMediaArray(responseModel: GetIdAndCaptionResponseModel) {
        medias.add(
            Media(
                id = responseModel.id.toLong(),
                caption = responseModel.caption
            )
        )
    }

    fun getMediaData(mediaId: String, accessToken: String) {
        _getMediaDataLiveData.value = MyResponse.loading()
        sendGetMediaDataRequest(mediaId, accessToken)
    }

    private fun sendGetMediaDataRequest(mediaId: String, accessToken: String) {
        compositeDisposable += getMediaDataUseCase.invoke(mediaId, accessToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let { data ->
                    for (media in medias) {
                        if (media.id == data.id.toLong()) {
                            media.mediaType = data.mediaType
                            media.mediaUrl = data.mediaUrl
                        }
                    }
                }
                _getMediaDataLiveData.value = it
            }, {
                _getMediaDataLiveData.value = MyResponse.failed(NetworkException())
            })
    }

    fun startDownloading(url: String) {
        var date = Date()
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val currentDateTime: String = formatter.format(date)
        val request: DownloadManager.Request =
            DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("Downloading file...")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "$currentDateTime.jpg"
        )
        _downloadLiveData.value = MyResponse.success(request)
    }

    fun checkWriteExternalStoragePermission() {
        _writeExternalStoragePermissionLiveData.value = MyResponse.loading()
        compositeDisposable += checkWriteExternalStoragePermissionUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _writeExternalStoragePermissionLiveData.value = it
            }, {})
    }
}