package com.libraryrapp.whispersink.repository

import com.libraryrapp.whispersink.model.Item
import com.libraryrapp.whispersink.network.BooksApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {

    private val _listState: MutableStateFlow<BookItemState> = MutableStateFlow(BookItemState.Loading)
    private var listState: StateFlow<BookItemState> = _listState
    suspend fun getBooks(query: String): StateFlow<BookItemState> {

        _listState.value = BookItemState.Loading

        return try {

            val itemList = api.getSearchBooks(query).items

            _listState.value = BookItemState.ListItem(itemList)

            listState

        } catch (exception: Exception) {

            _listState.value = BookItemState.Error(message = exception.message.toString())

            listState
        }
    }

    suspend fun getBookInfo(bookId: String): StateFlow<BookItemState> {

        _listState.value = BookItemState.Loading

        return try {

            val item = api.getBookInfo(bookId)

            _listState.value = BookItemState.ItemResponse(item)

            listState

        } catch (exception: Exception) {

            _listState.value = BookItemState.Error(message = exception.message.toString())

            listState
        }

    }

    suspend fun getFilteredBooks(query: String, filter: String): StateFlow<BookItemState> {

        return try {

            _listState.value = BookItemState.Loading

            val itemList = api.getFilterBooks(query, filter).items

            _listState.value = BookItemState.ListItem(itemList)

            listState

        } catch (exception: Exception) {

            _listState.value = BookItemState.Error(message = exception.message.toString())

            listState
        }
    }

    sealed interface BookItemState{
        data class ItemResponse(val data: Item): BookItemState
        data class ListItem(val data: List<Item>): BookItemState
        data class Error(val message: String): BookItemState
        object Loading: BookItemState
    }
}