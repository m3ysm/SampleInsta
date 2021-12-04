package com.example.sample.ui.usermedia.domain

import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.GetMediaDataResponseModel
import com.example.sample.data.model.MyResponse
import com.example.sample.data.repository.UserMediaRepository
import io.reactivex.Single
import retrofit2.Response

class GetMediaDataUseCase(private val repository: UserMediaRepository) {

    fun invoke(
        mediaId: String,
        accessToken: String
    ): Single<MyResponse<GetMediaDataResponseModel>> {
        return repository.getMediaData(mediaId, accessToken).flatMap {
            handleResponse(it)
        }
    }

    private fun handleResponse(response: Response<GetMediaDataResponseModel>): Single<MyResponse<GetMediaDataResponseModel>> {
        if (response.isSuccessful) {
            return Single.just(MyResponse.success(response.body()))
        }
        return Single.just(MyResponse.failed(NetworkException()))
    }
}