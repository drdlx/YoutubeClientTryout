package com.drdlx.youtubeclienttryout.mainScreen.view

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.drdlx.youtubeclienttryout.mainScreen.model.MainScreenUiState
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenView(
    uiState: MainScreenUiState,
    changeSearchQueryValue: (String) -> Unit,
    startSearching: () -> Unit,
    openVideo: (String) -> Unit,
) {

    val isLoading = uiState.isLoading.observeAsState(false)
    val videosFound = uiState.videosFound.observeAsState()
    val currentSearchValue = uiState.searchFieldValue.observeAsState()

    if (isLoading.value) {
        CircularProgressIndicator()
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val maxHeight = maxHeight
        val maxWidth = maxWidth
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val searchPanelHeight = maxHeight * 0.1f
                TextField(
                    modifier = Modifier
                        .width(maxWidth * 0.7f),
                    value = currentSearchValue.value ?: "",
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = changeSearchQueryValue,
                )
                Button(onClick = startSearching) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
            Row() {
                val videoListHeight = maxHeight * 0.9f
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(videoListHeight),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (!videosFound.value.isNullOrEmpty()) {
                        items(
                            videosFound.value!!
                        ) { videoItem ->
                            val title = videoItem.title
                            val thumbnailUrl = videoItem.thumbnailUrl
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = true,
                                        onClick = { openVideo(videoItem.id) }
                                    ),
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                AsyncImage(
                                    model = thumbnailUrl,
                                    contentDescription = "",
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(title)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun MainScreenViewPreview() {
//    MainScreenView()
}
