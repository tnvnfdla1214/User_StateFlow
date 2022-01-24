package com.example.user_stateflow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel
@ViewModelInject
constructor(private val userRepository: UserRepository) : ViewModel(){

    private val _expense = MutableStateFlow<State>(State.Empty)
    val expense: StateFlow<State> = _expense


    init {
        viewModelScope.launch{
            _expense.value = State.Loading
            when (val response = userRepository.getUser()) {
                is Resource.Error -> _expense.value = State.Failure(response.message!!)
                is Resource.Success -> _expense.value = State.Success(response.data!!)
            }
        }
    }

    fun getAllUser() =
        viewModelScope.launch(Dispatchers.IO) {
            _expense.value = State.Loading
            when (val response = userRepository.getUser()) {
                is Resource.Error -> _expense.value = State.Failure(response.message!!)
                is Resource.Success -> _expense.value = State.Success(response.data!!)
            }
        }

    fun insertUser(user:User) =
        viewModelScope.launch(Dispatchers.IO) {
            _expense.value = State.Loading
            userRepository.insert(user)

            when (val response = userRepository.getUser()) {
                is Resource.Error -> _expense.value = State.Failure(response.message!!)
                is Resource.Success -> _expense.value = State.Success(response.data!!)
            }
        }


//    val getUser:LiveData<List<User>> get() =
//        userRepository.getUser.flowOn(Dispatchers.Main)
//            .asLiveData(context = viewModelScope.coroutineContext)

//    fun insert(user:User){
//        viewModelScope.launch {
//            userRepository.insert(user)
//        }
//    }
}