package com.avwaveaf.gastropedia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gastronomy(
    val name:String,
    val category: String,
    val description:String,
    val imgUrl:String,
    val ingredients:String,
    val history:String
):Parcelable
