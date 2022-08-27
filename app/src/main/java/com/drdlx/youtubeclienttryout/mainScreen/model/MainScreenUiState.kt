package com.drdlx.youtubeclienttryout.mainScreen.model

import androidx.lifecycle.LiveData
import com.drdlx.youtubeclienttryout.network.SearchResponse

data class MainScreenUiState(
    val isLoading: LiveData<Boolean>,
    val videosFound: LiveData<List<VideoItem>>,
    val searchFieldValue: LiveData<String>,
)
