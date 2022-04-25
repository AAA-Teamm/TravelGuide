package com.ateam.travelguide.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

fun Context.openCamera(cameraResultLauncher: ActivityResultLauncher<Intent>) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val file = createImageFile()
    val imageUri = FileProvider.getUriForFile(this, this.packageName, file)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
    cameraResultLauncher.launch(intent)
}

fun Context.openGallery(galleryResultLauncher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    galleryResultLauncher.launch(intent)
}

@Throws(IOException::class)
fun Context.createImageFile(): File {
    val dir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile("image", ".jpg", dir).apply {
        absolutePath
    }
}

fun Activity.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    startActivity(intent)
}