package com.ateam.travelguide.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class Permissions {

    companion object {
        var cameraRequestList = ArrayList<String>()
        var galleryRequestList = ArrayList<String>()

        fun checkCameraPermission(context: Context): Boolean {
            var permissionState = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

            if (!permissionState) {
                cameraRequestList.add(Manifest.permission.CAMERA)
            }

            permissionState = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!permissionState) {
                cameraRequestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            permissionState = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!permissionState) {
                cameraRequestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            return cameraRequestList.size == 0
        }

        fun checkGalleryPermission(context: Context): Boolean {
            val permissionState = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!permissionState) {
                galleryRequestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            return galleryRequestList.size == 0
        }
    }

}