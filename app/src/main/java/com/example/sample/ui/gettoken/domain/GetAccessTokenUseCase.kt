package com.example.sample.ui.gettoken.domain

import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.GetAccessTokenResponseModel
import com.example.sample.data.model.MyResponse
import com.example.sample.data.repository.GetAccessTokenRepository
import io.reactivex.Single
import retrofit2.Response

class GetAccessTokenUseCase(private val repository: GetAccessTokenRepository) {

    fun invoke(
        clientId: String,
        clientSecret: String,
        grantType: String,
        redirectUri: String,
        code: String
    ): Single<MyResponse<GetAccessTokenResponseModel>> {
        return repository.getAccessToken(
            clientId,
            clientSecret,
            grantType,
            redirectUri,
            code
        ).flatMap {
            handleResponse(it)
        }
    }

    private fun handleResponse(response: Response<GetAccessTokenResponseModel>): Single<MyResponse<GetAccessTokenResponseModel>> {
        if (response.isSuccessful) {
            return Single.just(MyResponse.success(response.body()))
        }
        return Single.just(MyResponse.failed(NetworkException()))
    }
}