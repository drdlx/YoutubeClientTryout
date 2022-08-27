package com.drdlx.youtubeclienttryout.network

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.LiveBroadcast
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: List<Item>,
){
    data class Item(
        @SerializedName("etag")
        val etag: String,
        @SerializedName("id")
        val id: Id,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("snippet")
        val snippet: Snippet,
        @SerializedName("channelTitle")
        val channelTitle: String,
        @SerializedName("liveBroadcastContent")
        val liveBroadcastContent: String,
    ){
        data class Id(
            @SerializedName("kind")
            val kind: String,
            @SerializedName("videoId")
            val videoId: String?
        )

        data class Snippet(
            @SerializedName("publishedAt")
            val publishedAt: String,
            @SerializedName("channelId")
            val channelId: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails,
        ) {
            data class Thumbnails(
                @SerializedName("default")
                val default: Thumbnail,
                @SerializedName("medium")
                val medium: Thumbnail,
                @SerializedName("high")
                val high: Thumbnail,
            ) {
                data class Thumbnail(
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int,
                    @SerializedName("height")
                    val height: Int,
                )
            }
        }
    }
}