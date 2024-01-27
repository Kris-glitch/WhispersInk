package com.libraryrapp.whispersink.model

data class GoogleApiBook(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)