package com.libraryrapp.whispersink.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.libraryrapp.whispersink.model.MyBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val queryBook: Query
) {


    private val _booksState: MutableStateFlow<MyBookState> = MutableStateFlow(
        MyBookState.Loading)
    private var booksState: StateFlow<MyBookState> = _booksState
    suspend fun getAllBooksFromDatabase(): StateFlow<MyBookState> {

        _booksState.value = MyBookState.Loading

       return try {

            val myBookList = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MyBook::class.java)!!
            }

            _booksState.value = MyBookState.Success(myBookList)

           booksState


        } catch (exception: FirebaseFirestoreException) {
            _booksState.value = MyBookState.Error(message = exception.message.toString())

           booksState
        }

    }

    fun saveBookInDB(book: MyBook): Result<Unit> {

        var result: Result<Unit> = Result.failure(Exception())


        val db = FirebaseFirestore.getInstance()
        val dbCollection = db.collection("books")

        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            result = Result.success(Unit)
                        }


                    }.addOnFailureListener {
                        result = Result.failure(it)
                    }
            }
        return result
    }
    suspend fun startReading(book: MyBook) {

        val db = FirebaseFirestore.getInstance()
        val dbCollection = db.collection("books")

        val querySnapshot = dbCollection.whereEqualTo("googleBookId", book.googleBookId).get().await()

        if (!querySnapshot.isEmpty) {

            val documentSnapshot = querySnapshot.documents.first()
            val existingDocId = documentSnapshot.id
            dbCollection.document(existingDocId).set(book).await()

        } else {
            dbCollection.add(book).await()
        }
    }

    suspend fun deleteBookFromDb(googleBookId: String){
        val db = FirebaseFirestore.getInstance()
        val dbCollection = db.collection("books")

        val querySnapshot = dbCollection.whereEqualTo("googleBookId", googleBookId).get().await()

        querySnapshot.documents.forEach { document ->
            dbCollection.document(document.id).delete().await()
        }
    }
    sealed interface MyBookState{
        data class Success(val data: List<MyBook>): MyBookState
        data class Error(val message: String): MyBookState
        object Loading: MyBookState
    }
}
