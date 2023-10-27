package com.oshanti.seersolution.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oshanti.seersolution.base.UiState
import com.oshanti.seersolution.data.local.repository.UserRepo
import com.oshanti.seersolution.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private var _dbInsertState = MutableLiveData<UiState<Long>>()
    val dbInsertState: LiveData<UiState<Long>> = _dbInsertState

    private var _getUser = MutableLiveData<UiState<User>>()
    val getUser: LiveData<UiState<User>> = _getUser

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepo.getUserById(user.userId)
            withContext(Dispatchers.Main) {
                if (userId == null) {
                    val state = userRepo.insertUser(user)
                    _dbInsertState.value = UiState.Success(state)
                } else {
                    _getUser.value = UiState.Success(userId)
                }
            }
        }
    }
}