package com.sanjaydevtech.sampleiconfinder.ui.screens.download

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.sanjaydevtech.sampleiconfinder.data.IconFinderService
import com.sanjaydevtech.sampleiconfinder.data.NetworkResult
import com.sanjaydevtech.sampleiconfinder.data.models.Icon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val service: IconFinderService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var networkResult: NetworkResult by savedStateHandle.saveable {
        mutableStateOf(NetworkResult.Loading)
    }

    var icon: Icon by savedStateHandle.saveable {
        mutableStateOf(Icon(0, emptyList(), false, ""))
    }

    suspend fun getFile(format: String, size: Int): ResponseBody? {
        return try {
            service.downloadIcon(
                iconId = savedStateHandle.get<Int>("id") ?: 0,
                format = format,
                size = size
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    init {
        viewModelScope.launch {
            Snapshot.withMutableSnapshot {
                networkResult = NetworkResult.Loading
            }
            try {
                val response =
                    service.getIconDetails(savedStateHandle.get<Int>("id") ?: 0)
                Snapshot.withMutableSnapshot {
                    icon = (response ?: icon)
                    networkResult = (NetworkResult.Data)
                }
            } catch (e: Exception) {
                Snapshot.withMutableSnapshot {
                    networkResult = (NetworkResult.Error(e))
                }
            }
        }
    }

}