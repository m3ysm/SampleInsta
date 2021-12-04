package com.example.sample.data.webservice

import com.example.sample.data.model.GetAccessTokenResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InstagramBasicDisplayAPI {

    @FormUrlEncoded
    @POST("oauth/access_token")
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String
    ): Single<Response<GetAccessTokenResponseModel>>
}