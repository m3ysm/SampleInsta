package com.example.sample.ui.gettoken

import androidx.lifecycle.LiveData
import com.example.sample.data.exception.common.NetworkException
import com.example.sample.data.model.GetAccessTokenResponseModel
import com.example.sample.data.model.MyResponse
import com.example.sample.ui.base.BaseViewModel
import com.example.sample.ui.gettoken.domain.GetAccessTokenUseCase
import com.example.sample.util.SingleLiveEvent
import com.example.sample.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.inject

class GetTokenViewModel : BaseViewModel() {

    private val getAccessTokenUseCase: GetAccessTokenUseCase by inject()

    private val _getAccessTokenLiveData =
        SingleLiveEvent<MyResponse<GetAccessTokenResponseModel>>()
    val getAccessTokenLiveData: LiveData<MyResponse<GetAccessTokenResponseModel>>
        get() = _getAccessTokenLiveData

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grantType: String,
        redirectUri: String,
        code: String
    ) {
        _getAccessTokenLiveData.value = MyResponse.loading()
        sendGetAccessTokenLiveData(
            clientId,
            clientSecret,
            grantType,
            redirectUri,
            code
        )
    }

    private fun sendGetAccessTokenLiveData(
        clientId: String,
        clientSecret: String,
        grantType: String,
        redirectUri: String,
        code: String
    ) {
        compositeDisposable += getAccessTokenUseCase.invoke(
            clientId,
            clientSecret,
            grantType,
            redirectUri,
            code
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _getAccessTokenLiveData.value = it
            }, {
                _getAccessTokenLiveData.value = MyResponse.failed(NetworkException())
            })
    }
}