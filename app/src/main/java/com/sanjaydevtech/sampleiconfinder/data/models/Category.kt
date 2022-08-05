package com.sanjaydevtech.sampleiconfinder.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    val identifier: String,
) : Parcelable
