package com.example.sample.data.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
class StorageService(val context: Context) {

    companion object {
        private const val PERMISSION_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun isWriteExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, PERMISSION_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}