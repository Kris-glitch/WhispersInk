package com.libraryrapp.whispersink.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libraryrapp.whispersink.data.DataOrException
import com.libraryrapp.whispersink.model.Item
import com.libraryrapp.whispersink.model.MyBook
import com.libraryrapp.whispersink.network.FirebaseUserUtils
import com.libraryrapp.whispersink.repository.BookRepository
import com.libraryrapp.whispersink.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _bookUiState: MutableStateFlow<DataOrException<MyBook, Boolean, Exception>> =
        MutableStateFlow(DataOrException(null, true, Exception("")))

    val bookUiState: StateFlow<DataOrException<MyBook, Boolean, Exception>> =
        _bookUiState


    fun getBookInfo(id: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (id.isEmpty()) {
                return@launch
            }
            try {
                _bookUiState.update {
                    it.copy(loading = true)
                }

                val response = bookRepository.getBookInfo(id).first()

                when (response) {

                    is BookRepository.BookItemState.ItemResponse -> {

                        val book = mapItemToMyBook(response.data)

                        _bookUiState.update {
                            it.copy(loading = false, data = book)
                        }

                    }

                    is BookRepository.BookItemState.Error -> {

                        _bookUiState.update {
                            it.copy(loading = false, e = Exception(response.message))
                        }

                    }

                    else -> {
                        _bookUiState.update {
                            it.copy(loading = true)
                        }

                    }
                }

            } catch (exception: Exception) {
                _bookUiState.update {
                    it.copy(loading = false, e = Exception(exception.message))
                }
            }

        }
    }

    fun saveBook(): Boolean {
        try {
            val book = bookUiState.value.data ?: return false

            book.inMyList = true

            val result = firebaseRepository.saveBookInDB(book)

            return result.isSuccess

        } catch (e: Exception) {

            return false
        }
    }

    fun startReadingBook() {
        viewModelScope.launch {

            val book = bookUiState.value.data

            if (book != null) {

                book.inMyList = true
                book.startedReading = true

                firebaseRepository.startReading(book)
            }



        }
    }

    private fun mapItemToMyBook(data: Item): MyBook {

        val volume = data.volumeInfo


        return MyBook(
            title = volume.title,
            authors = volume.authors,
            photoUrl = volume.imageLinks.thumbnail,
            categories = volume.categories,
            publishedDate = volume.publishedDate,
            rating = volume.averageRating,
            description = volume.description,
            pageCount = volume.pageCount.toString(),
            googleBookId = data.id,
            userId = FirebaseUserUtils.getUserId()
        )

    }

}