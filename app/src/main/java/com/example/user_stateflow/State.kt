package com.example.user_stateflow

sealed class State {
    class Success(val List: List<User>): State()
    class Failure(val message: String): State()
    object Loading: State()
    object Empty: State()
}