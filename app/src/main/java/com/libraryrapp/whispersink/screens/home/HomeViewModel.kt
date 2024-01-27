package com.libraryrapp.whispersink.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libraryrapp.whispersink.R
import com.libraryrapp.whispersink.data.DataOrException
import com.libraryrapp.whispersink.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.libraryrapp.whispersink.model.Item
import com.libraryrapp.whispersink.model.MyBook
import com.libraryrapp.whispersink.repository.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

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

                val response = bookRepository.getBooks(query).first()


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

                val response = bookRepository.getFilteredBooks(query, filter).first()

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

    fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                _uiStateSearchBooks.update {
                    it.copy(loading = true)
                }

                val response = firebaseRepository.getAllBooksFromDatabase().first()

                when (response) {

                    is FirebaseRepository.MyBookState.Success -> {

                        val listOfBooks = response.data

                        if (listOfBooks.isNotEmpty()) {
                            _uiStateSearchBooks.update {
                                it.copy(loading = false, data = listOfBooks)
                            }
                        }

                    }

                    is FirebaseRepository.MyBookState.Error -> {

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

    fun getSelectedOption(selectedOption: Int, listOfBooks: List<MyBook>?) {


        val filteredList = when (selectedOption) {
            R.string.reading -> listOfBooks?.filter { it.startedReading == true } ?: emptyList()
            R.string.not_reading -> listOfBooks?.filter { it.startedReading == null } ?: emptyList()
            R.string.finished -> listOfBooks?.filter { it.finishedReading == true } ?: emptyList()
            else -> emptyList()
        }


        if (filteredList.isNotEmpty()) {
            _uiStateSearchBooks.update {
                it.copy(loading = false, data = filteredList)
            }
        }

    }

    fun deleteFromDatabase(googleBookId: String) {

        viewModelScope.launch(Dispatchers.IO) {

            firebaseRepository.deleteBookFromDb(googleBookId)

            getBooksFromDatabase()
        }
    }


}