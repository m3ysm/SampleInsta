package com.example.sample.ui.usermedia.domain

import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.ApiResponse
import com.example.sample.data.model.GetIdAndCaptionResponseModel
import com.example.sample.data.model.MyResponse
import com.example.sample.data.repository.UserMediaRepository
import io.reactivex.Single
import retrofit2.Response

class GetUserMediaIdAndCaptionUseCase(private val repository: UserMediaRepository) {

    fun invoke(
        userId: String,
        accessToken: String
    ): Single<MyResponse<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>> {
        return repository.getUserMediaIdAndCaption(userId, accessToken).flatMap {
            handleResponse(it)
        }
    }

    private fun handleResponse(response: Response<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>): Single<MyResponse<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>> {
        if (response.isSuccessful) {
            return Single.just(MyResponse.success(response.body()))
        }
        return Single.just(MyResponse.failed(NetworkException()))
    }
}