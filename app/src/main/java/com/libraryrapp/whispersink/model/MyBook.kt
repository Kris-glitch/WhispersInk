package com.libraryrapp.whispersink.model

data class MyBook(
    var title: String? = null,
    var authors: List<String>? = null,
    var photoUrl: String? = null,
    var categories: List<String>? = null,
    var publishedDate: String? = null,
    var rating: Double? = null,
    var description: String? = null,
    var pageCount: String? = null,
    var startedReading: Boolean? = null,
    var finishedReading: Boolean? = null,
    var userId: String? = null,
    var googleBookId: String,
    var inMyList: Boolean? = null
)
