package com.libraryrapp.whispersink.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libraryrapp.whispersink.data.DataOrException
import com.libraryrapp.whispersink.model.Item
import com.libraryrapp.whispersink.model.MyBook
import com.libraryrapp.whispersink.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

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

                val response = repository.getBookInfo(id).first()

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
            googleBookId = data.id
        )

    }

}