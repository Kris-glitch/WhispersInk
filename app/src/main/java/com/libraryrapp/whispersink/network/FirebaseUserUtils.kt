package com.libraryrapp.whispersink.network

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.libraryrapp.whispersink.model.MyUsers
import kotlinx.coroutines.tasks.await


object FirebaseUserUtils {

    private val auth: FirebaseAuth = Firebase.auth

    fun isCurrentUserLoggedIn(): Boolean {
        return auth.currentUser?.email.isNullOrEmpty()
    }
    suspend fun logInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val displayName = result.user?.email?.split('@')?.get(0)

            if (!displayName.isNullOrEmpty()) {
                createUserInDB(displayName)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createUserInDB(displayName: String?) {
        val userId = auth.currentUser?.uid

        val user = MyUsers(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            id = null).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }

    fun logOut() {
        auth.signOut()
    }

}