package com.libraryrapp.whispersink.network

import com.libraryrapp.whispersink.model.GoogleApiBook
import com.libraryrapp.whispersink.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getSearchBooks(@Query("q") query: String): GoogleApiBook

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item

    @GET("volumes")
    suspend fun getFilterBooks(
        @Query("q") query: String,
        @Query("filter") filter: String
    ): GoogleApiBook

}