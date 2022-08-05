package com.sanjaydevtech.sampleiconfinder.ui.screens.download

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sanjaydevtech.sampleiconfinder.R
import com.sanjaydevtech.sampleiconfinder.data.models.RasterSize
import com.sanjaydevtech.sampleiconfinder.ui.comps.LoadImage

@Composable
fun DownloadView(
    isPremium: Boolean,
    rasterSize: RasterSize,
    onDownloadClick: (String, Int) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Size: ${rasterSize.size}")
        LoadImage(
            rasterSize.formats.first().preview_url,
            modifier = Modifier
                .height(200.dp)
                .width(150.dp)
        )
        LazyRow {
            items(rasterSize.formats, key = { it.format }) {
                if (isPremium) {
                    IconButton(onClick = {
                        Toast.makeText(context, "Icon is not purchased", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                            contentDescription = "Premium"
                        )
                    }
                } else {
                    IconButton(onClick = {
                        onDownloadClick(it.format, rasterSize.size)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_cloud_download_24),
                            contentDescription = "Save"
                        )
                    }
                }
            }
        }
    }
}