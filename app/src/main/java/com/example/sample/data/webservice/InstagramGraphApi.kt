package com.example.sample.data.webservice

import com.example.sample.data.model.ApiResponse
import com.example.sample.data.model.GetIdAndCaptionResponseModel
import com.example.sample.data.model.GetMediaDataResponseModel
import com.example.sample.data.model.GetUserNameResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InstagramGraphApi {

    @GET("{user-id}?fields=id,username")
    fun getUserName(
        @Path("user-id") userId: String,
        @Query("access_token") accessToken: String
    ): Single<Response<GetUserNameResponseModel>>

    @GET("{user-id}/media?fields=id,caption")
    fun getUserMediaIdAndCaption(
        @Path("user-id") userId: String,
        @Query("access_token") accessToken: String
    ): Single<Response<ApiResponse<ArrayList<GetIdAndCaptionResponseModel>>>>

    @GET("{media-id}?fields=id,media_type,media_url,username,timestamp")
    fun getMediaData(
        @Path("media-id") mediaId: String,
        @Query("access_token") accessToken: String
    ): Single<Response<GetMediaDataResponseModel>>
}