package com.libraryrapp.whispersink.model

import com.google.firebase.Timestamp

data class MyBook(
    var title: String? = null,
    var authors: List<String>? = null,
    var photoUrl: String? = null,
    var categories: List<String>? = null,
    var publishedDate: String? = null,
    var rating: Double? = null,
    var description: String? = null,
    var pageCount: String? = null,
    var startedReading: Timestamp? = null,
    var finishedReading: Timestamp? = null,
    var userId: String? = null,
    var googleBookId: String? = null
)
