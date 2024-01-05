package com.libraryrapp.whispersink.model

data class MyUsers(
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "avatar_url" to this.avatarUrl
        )
    }

}