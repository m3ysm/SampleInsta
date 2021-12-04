package com.example.sample.ui.usermedia.domain

import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.GetUserNameResponseModel
import com.example.sample.data.model.MyResponse
import com.example.sample.data.repository.UserMediaRepository
import io.reactivex.Single
import retrofit2.Response

class GetUserNameUseCase(private val repository: UserMediaRepository) {

    fun invoke(userId: String, accessToken: String): Single<MyResponse<GetUserNameResponseModel>> {
        return repository.getUserName(userId, accessToken).flatMap {
            handleResponse(it)
        }
    }

    private fun handleResponse(response: Response<GetUserNameResponseModel>): Single<MyResponse<GetUserNameResponseModel>> {
        if (response.isSuccessful) {
            return Single.just(MyResponse.success(response.body()))
        }
        return Single.just(MyResponse.failed(NetworkException()))
    }
}