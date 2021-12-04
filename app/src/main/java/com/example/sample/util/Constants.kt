package com.example.sample.util

object Constants {

    const val REQUEST_TIME_OUT = 50L
    const val CLIENT_ID = "594641718510023"
    const val CLIENT_SECRET = "00748a17ffa2e84d7eab18d5da514b1b"
    const val GRANT_TYPE = "authorization_code"
    const val REDIRECT_URL = "https://www.google.com/?code="
    const val INSTAGRAM_BASIC_DISPLAY_BASE_URL = "https://api.instagram.com/"
    const val INSTAGRAM_GRAPH_BASE_URL = "https://graph.instagram.com/"
    const val AUTHENTICATE_USER_URL = " https://api.instagram.com/oauth/authorize\n" +
            "?client_id=594641718510023\n" +
            "&redirect_uri=https://www.google.com/\n" +
            "&scope=user_profile,user_media\n" +
            "&response_type=code"
}