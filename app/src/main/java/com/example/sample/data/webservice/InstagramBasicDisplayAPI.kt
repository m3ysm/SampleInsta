package com.example.sample.data.webservice

import com.example.sample.data.model.ApiResponse
import com.example.sample.data.model.GetAccessTokenRequestModel
import com.example.sample.data.model.GetAccessTokenResponseModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET

interface InstagramBasicDisplayAPI {

    @GET("oauth/access_token")
    fun getAccessToken(@Body getAccessTokenRequestModel: GetAccessTokenRequestModel)
            : Single<ApiResponse<GetAccessTokenResponseModel>>
}