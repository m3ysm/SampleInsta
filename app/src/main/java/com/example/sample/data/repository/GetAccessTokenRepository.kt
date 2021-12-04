package com.example.sample.data.repository

import com.example.sample.data.model.GetAccessTokenResponseModel
import com.example.sample.data.webservice.InstagramBasicDisplayAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class GetAccessTokenRepository(private val rest: InstagramBasicDisplayAPI) : BaseRepository() {

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grantType: String,
        redirectUri: String,
        code: String
    ): Single<Response<GetAccessTokenResponseModel>> {
        return rest.getAccessToken(
            clientId,
            clientSecret,
            grantType,
            redirectUri,
            code
        ).subscribeOn(Schedulers.io())
    }
}