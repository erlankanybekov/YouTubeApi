package com.example.youtubeapi.data.models

import com.google.gson.annotations.SerializedName

data class Playlists(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)
data class Item(
    @SerializedName("contentDetails")
    val contentDetails: ContentDetails,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("items")
    val items: List<Item>
)
data class Snippet (
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("localized")
    val localized: Localized,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String,
    @SerializedName("videoId")
    var videoId: String
)
data class ContentDetails(
    @SerializedName("itemCount")
    val itemCount: Int
)

data class Default (
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class High (
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
data class Medium (
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
data class Maxres (
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
data class Standard (
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
data class Thumbnails (
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("maxres")
    val maxres: Maxres,
    @SerializedName("medium")
    val medium: Medium,
    @SerializedName("standard")
    val standard: Standard
)
data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)
data class Localized(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)

