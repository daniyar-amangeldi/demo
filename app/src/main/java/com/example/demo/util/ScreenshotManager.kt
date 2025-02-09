package com.example.demo.util

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.util.*


object ScreenshotManager {

    private const val SCREENSHOT_EXTENSION = ".jpg"
    private const val SCREENSHOT_NAME_PREFIX = "movie_screenshot"

    fun takeScreenshot(view: View, onFailure: () -> Unit): Uri? {
        var screenshot: Bitmap? = null

        try {
            screenshot = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            view.draw(canvas)
        } catch (e: Exception) {
            onFailure()
        }

        screenshot?.let {
            return saveImageExternal(view.context, it, onFailure)
        }

        return null
    }

    private fun saveImageExternal(context: Context, image: Bitmap, onFailure: () -> Unit): Uri? {
        var uri: Uri? = null

        try {
            val timeStamp: String = Date().toString()
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "$SCREENSHOT_NAME_PREFIX$timeStamp",
                SCREENSHOT_EXTENSION,
                storageDir
            )
            val stream = FileOutputStream(file)

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
            uri = FileProvider.getUriForFile(
                context,
                "com.example.demo.provider",
                file
            )
        } catch (e: Exception) {
            onFailure()
        }

        return uri
    }
}