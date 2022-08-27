package com.drdlx.youtubeclienttryout.network

import com.drdlx.youtubeclienttryout.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/")
    fun search(
        @Query("part") additionalPropertySearch: String = "snippet",
        @Query("q") searchString: String,
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("maxResults") maxResults: Int = 25,
    ) : Call<SearchResponse>
}