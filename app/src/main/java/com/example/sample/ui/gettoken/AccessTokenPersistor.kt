package com.example.sample.ui.gettoken

import android.content.Context

object AccessTokenPersistor {

    lateinit var context: Context

    private const val SHARED_PREFERENCE_NAME = "accessToken"
    private const val TOKEN_NAME = "token"
    private const val USER_ID = "user_id"
    private const val SHARED_PREFERENCE_MODE = Context.MODE_PRIVATE

    fun getInstance(context: Context): AccessTokenPersistor {
        AccessTokenPersistor.context = context
        return this
    }

    fun save(token: String, userId: Long) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, SHARED_PREFERENCE_MODE)
        sharedPreferences.edit().let {
            it.putString(TOKEN_NAME, token)
            it.putLong(USER_ID, userId)
            it.commit()
        }
    }

    fun getToken(): String {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, SHARED_PREFERENCE_MODE)
        val token = sharedPreferences.getString(TOKEN_NAME, "")!!
        return token
    }

    fun getUserId(): Long {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, SHARED_PREFERENCE_MODE)
        val userId = sharedPreferences.getLong(USER_ID, 0)
        return userId
    }

    fun remove() {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, SHARED_PREFERENCE_MODE)
        sharedPreferences.edit().let {
            it.remove(TOKEN_NAME)
            it.remove(USER_ID)
            it.commit()
        }
    }
}