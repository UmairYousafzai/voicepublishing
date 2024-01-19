package com.shadow.voicepublishing.view.models

import androidx.lifecycle.*

import com.google.firebase.auth.FirebaseAuth
import com.shadow.voicepublishing.models.netwrok.auth.User
import com.shadow.voicepublishing.repositories.data.abstraction.DataStoreRepository
import com.shadow.voicepublishing.utils.IS_LOGIN
import com.shadow.voicepublishing.utils.IS_SUBSCRIBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val repository: DataStoreRepository) : ViewModel() {

    fun put(key: String, value: String) {
        viewModelScope.launch {
            repository.putString(key, value)
        }
    }

    fun put(key: String, value: Long) {
        viewModelScope.launch {
            repository.putLong(key, value)
        }
    }


    fun put(key: String, value: Int) {
        viewModelScope.launch {
            repository.putInt(key, value)
        }
    }


    fun put(key: String, value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(key, value)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.saveUserInfo(user)
        }
    }


    fun clearData() = viewModelScope.launch {
        repository.clearData()
        put(IS_LOGIN, false)
        put(IS_SUBSCRIBE, false)
        FirebaseAuth.getInstance().signOut()
    }

    var user = repository.user

    val isSubscribeStatus = repository.isSubscribe
    val alreadyLoginStatus = repository.isAlreadyLogin

}