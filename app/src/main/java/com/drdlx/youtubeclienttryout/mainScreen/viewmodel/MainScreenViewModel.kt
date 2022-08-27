package com.drdlx.youtubeclienttryout.mainScreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drdlx.youtubeclienttryout.mainScreen.model.MainScreenUiState
import com.drdlx.youtubeclienttryout.network.SearchResponse
import com.drdlx.youtubeclienttryout.network.YoutubeApi
import retrofit2.Callback
import android.util.Log
import com.drdlx.youtubeclienttryout.mainScreen.model.VideoItem
import retrofit2.Call
import retrofit2.Response

class MainScreenViewModel: ViewModel() {

    companion object {
        private const val TAG = "MainScreenViewModel"
    }

    private val _isLoading = MutableLiveData(false)
    private val _videosFound = MutableLiveData<List<VideoItem>>()
    private val _searchQuery = MutableLiveData<String>()

    val uiState = MainScreenUiState(
        isLoading = _isLoading,
        videosFound = _videosFound,
        searchFieldValue = _searchQuery,
    )

    fun changeSearchQueryValue(searchQuery: String) {
        _searchQuery.postValue(searchQuery)
    }

    fun search() {
        _isLoading.postValue(true)
        val query = _searchQuery.value
        with(YoutubeApi) {
            if (query != null) {
                apiInstance().search(
                    searchString = query
                ).enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        println(response)
                        println(response.body())
                        Log.d(TAG, "onResponse: ${response.isSuccessful}")
                        val result = response.body()?.items
                        val testResult = result?.mapNotNull {
                            if (it.id.videoId == null) null else
                                VideoItem(
                                    id = it.id.videoId,
                                    title = it.snippet.title,
                                    description = it.snippet.description,
                                    thumbnailUrl = it.snippet.thumbnails.medium.url
                                )
                        }.also {
                            _videosFound.postValue(it)
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.e(TAG, "An error occurred")
                        Log.e(TAG, call.request().toString())
                        Log.e(TAG, t.message.toString())
                        Log.e(TAG, t.stackTraceToString())
                    }
                })
            } else Log.i(TAG, "Search request is empty!")
        }
        _isLoading.postValue(false)

    }

}