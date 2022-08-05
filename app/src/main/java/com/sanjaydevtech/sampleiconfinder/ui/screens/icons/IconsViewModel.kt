package com.sanjaydevtech.sampleiconfinder.ui.screens.icons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
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
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class IconsViewModel @Inject constructor(
    private val service: IconFinderService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var networkResult: NetworkResult by savedStateHandle.saveable {
        mutableStateOf(NetworkResult.Loading)
    }

    var icons: List<Icon> by savedStateHandle.saveable {
        mutableStateOf(emptyList())
    }

    init {
        viewModelScope.launch {
            withMutableSnapshot {
                networkResult = NetworkResult.Loading
            }
            try {
                val response =
                    service.listAllIconsInAIconSet(savedStateHandle.get<Int>("id") ?: 0)
                withMutableSnapshot {
                    icons = (response.icons)
                    networkResult = (NetworkResult.Data)
                }
            } catch (e: Exception) {
                withMutableSnapshot {
                    networkResult = (NetworkResult.Error(e))
                }
            }
        }
    }
}