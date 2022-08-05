package com.sanjaydevtech.sampleiconfinder.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class NetworkResult : Parcelable {
    object Loading : NetworkResult()
    class Error(val exception: Exception) : NetworkResult()
    object Data : NetworkResult()
}
