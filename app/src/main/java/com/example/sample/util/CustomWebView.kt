package com.example.sample.util

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.sample.data.model.progressbar.ProgressBarStatus
import com.example.sample.widget.ProgressLayout

class CustomWebView {
    @SuppressLint("SetJavaScriptEnabled")
    fun init(
        webView: WebView,
        progressbar: ProgressLayout,
        activity: Activity,
        listener: ((code: String?) -> Unit)? = null
    ) {
        webView.apply {
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            webViewClient = object : WebViewClient() {
                override fun doUpdateVisitedHistory(
                    view: WebView?,
                    url: String?,
                    isReload: Boolean
                ) {
                    if (!url.isNullOrBlank() && url.contains(Constants.REDIRECT_URL)) {
                        val code =
                            url.substring(url.indexOf("=") + 1, url.indexOf("#"))
                        listener?.invoke(code)
                    }
                    super.doUpdateVisitedHistory(view, url, isReload)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progressbar.setStatus(ProgressBarStatus.LOADING)
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    progressbar.setStatus(ProgressBarStatus.DONE)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressbar.setStatus(ProgressBarStatus.DONE)
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    handler?.proceed()
                }
            }
            this.loadUrl(Constants.AUTHENTICATE_USER_URL)
            this.setOnKeyListener { _, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@setOnKeyListener true
                }
                if ((keyCode == KeyEvent.KEYCODE_BACK) && this.canGoBack()) {
                    this.goBack()
                } else {
                    activity.onBackPressed()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }
}