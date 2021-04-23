package com.edwin.recipeapp.presentation.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Bitmap.saveToGallery(context: Context) {
    val fileName = "${System.currentTimeMillis()}.png"
    val fileType = "image/png"
    val folder = "RecipeApp"
    val imageQuality = 100

    val write: (OutputStream) -> Boolean = { outputStream ->
        this.compress(Bitmap.CompressFormat.PNG, imageQuality, outputStream)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, fileType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$folder")
        }

        context.contentResolver.let {
            it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                it.openOutputStream(uri)?.let(write)
            }
        }
    } else {
        @Suppress("DEPRECATION")
        val imagesDir = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + File.separator + folder
        val file = File(imagesDir)
        if (!file.exists()) {
            file.mkdir()
        }
        val image = File(imagesDir, fileName)
        write(FileOutputStream(image))
    }
}