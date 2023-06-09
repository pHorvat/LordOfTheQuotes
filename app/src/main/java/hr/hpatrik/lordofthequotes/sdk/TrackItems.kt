package hr.hpatrik.lordofthequotes.sdk

import android.graphics.Bitmap

data class TrackItems(
    val id: String = "",
    val title:String = "",
    val uri: String = "",
    val imageUri: Bitmap? = null,
    val subtitle:String = ""
)
