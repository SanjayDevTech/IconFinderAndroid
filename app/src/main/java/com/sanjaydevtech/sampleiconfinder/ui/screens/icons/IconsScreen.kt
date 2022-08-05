package com.sanjaydevtech.sampleiconfinder.ui.screens.icons

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sanjaydevtech.sampleiconfinder.data.NetworkResult
import com.sanjaydevtech.sampleiconfinder.ui.comps.AppBar
import com.sanjaydevtech.sampleiconfinder.ui.comps.EmptyView
import com.sanjaydevtech.sampleiconfinder.ui.comps.ErrorView
import com.sanjaydevtech.sampleiconfinder.ui.comps.LoadingView
import com.sanjaydevtech.sampleiconfinder.ui.screens.Screens
import com.sanjaydevtech.sampleiconfinder.ui.screens.iconsets.IconsetView

@Composable
fun IconsScreen(
    navController: NavController,
    title: String,
) {
    val viewModel = hiltViewModel<IconsViewModel>()
    val icons = viewModel.icons
    val result = viewModel.networkResult
    LaunchedEffect(key1 = result) {
        if (result is NetworkResult.Error) {
            (result.exception).printStackTrace()
        }
    }
    Scaffold(
        topBar = {
            AppBar(title, navController)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (result) {
                NetworkResult.Loading -> {
                    LoadingView()
                }
                NetworkResult.Data -> {
                    if (icons.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(3)
                        ) {
                            items(icons, key = { it.icon_id }) {
                                IconsView(it) {
                                    navController.navigate(
                                        Screens.DownloadScreen.args(
                                            it.icon_id,
                                            "$title - Download",
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    ErrorView()
                }
            }
        }
    }
}