package com.example.sample.data.repository

import com.example.sample.data.model.ApiResponse
import com.example.sample.data.model.GetIdAndCaptionResponseModel
import com.example.sample.data.model.GetMediaDataResponseModel
import com.example.sample.data.model.GetUserNameResponseModel
import com.example.sample.data.webservice.InstagramGraphApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserMediaRepository(private val rest: InstagramGraphApi) : BaseRepository() {

    fun getUserName(userId: String, accessToken: String)
            : Single<Response<GetUserNameResponseModel>> {
        return rest.getUserName(userId, accessToken).subscribeOn(Schedulers.io())
    }

    fun getUserMediaIdAndCaption(userId: String, accessToken: String)
            : Single<Response<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>> {
        return rest.getUserMediaIdAndCaption(userId, accessToken).subscribeOn(Schedulers.io())
    }

    fun getMediaData(mediaId: String, accessToken: String)
            : Single<Response<GetMediaDataResponseModel>> {
        return rest.getMediaData(mediaId, accessToken).subscribeOn(Schedulers.io())
    }
}