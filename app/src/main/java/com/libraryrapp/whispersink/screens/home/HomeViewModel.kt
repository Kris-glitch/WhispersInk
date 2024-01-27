package com.libraryrapp.whispersink.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libraryrapp.whispersink.data.DataOrException
import com.libraryrapp.whispersink.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.libraryrapp.whispersink.model.Item
import com.libraryrapp.whispersink.model.MyBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private var _uiStateSearchBooks: MutableStateFlow<DataOrException<List<MyBook>, Boolean, Exception>> =
        MutableStateFlow(DataOrException(null, true, Exception("")))

    val uiStateSearchBooks: StateFlow<DataOrException<List<MyBook>, Boolean, Exception>> =
        _uiStateSearchBooks

    init {
        searchBooks("orderBy=newest")
    }

    fun refresh() {
        searchBooks("orderBy=newest")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            if (query.isEmpty()) {
                return@launch
            }
            try {
                _uiStateSearchBooks.update {
                    it.copy(loading = true)
                }

                val response = repository.getBooks(query).first()


                when (response) {

                    is BookRepository.BookItemState.ListItem -> {

                        val listOfBooks = mapItemToMyBook(response.data)

                        if (listOfBooks.isNotEmpty()) {
                            _uiStateSearchBooks.update {
                                it.copy(loading = false, data = listOfBooks)
                            }
                        }

                    }

                    is BookRepository.BookItemState.Error -> {

                        _uiStateSearchBooks.update {
                            it.copy(loading = false, e = Exception(response.message))
                        }


                    }

                    else -> {
                        _uiStateSearchBooks.update {
                            it.copy(loading = true)
                        }
                    }
                }

            } catch (exception: Exception) {
                _uiStateSearchBooks.update {
                    it.copy(loading = false, e = Exception(exception.message))
                }
            }

        }
    }

    fun searchCategoryBooks(query: String, filter: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _uiStateSearchBooks.update {
                    it.copy(loading = true)
                }

                val response = repository.getFilteredBooks(query, filter).first()

                when (response) {

                    is BookRepository.BookItemState.ListItem -> {

                        val listOfBooks = mapItemToMyBook(response.data)

                        if (listOfBooks.isNotEmpty()) {
                            _uiStateSearchBooks.update {
                                it.copy(loading = false, data = listOfBooks)
                            }
                        }

                    }

                    is BookRepository.BookItemState.Error -> {

                        _uiStateSearchBooks.update {
                            it.copy(loading = false, e = Exception(response.message))
                        }


                    }

                    else -> {
                        _uiStateSearchBooks.update {
                            it.copy(loading = true)
                        }
                    }
                }

            } catch (exception: Exception) {
                _uiStateSearchBooks.update {
                    it.copy(loading = false, e = Exception(exception.message))
                }
            }

        }

    }

    private fun mapItemToMyBook(data: List<Item>): List<MyBook> {

        val resultList = mutableListOf<MyBook>()

        for (room in data) {
            val volume = room.volumeInfo

            val book = MyBook(
                title = volume.title,
                authors = volume.authors,
                photoUrl = volume.imageLinks.thumbnail,
                categories = volume.categories,
                publishedDate = volume.publishedDate,
                rating = volume.averageRating,
                description = volume.description,
                pageCount = volume.pageCount.toString(),
                googleBookId = room.id
            )
            resultList.add(book)

        }

        return resultList
    }


}