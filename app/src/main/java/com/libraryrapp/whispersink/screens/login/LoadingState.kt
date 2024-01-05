package com.libraryrapp.whispersink.screens.login

data class LoadingState(val status: Status, val message: String? = null) {

    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.LOADING)
        val ERROR = LoadingState(Status.ERROR, null)
    }

    enum class Status {

        SUCCESS,
        ERROR,
        LOADING,
        IDLE

    }


}
