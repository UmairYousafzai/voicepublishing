package com.shadow.voicepublishing.view.models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow.voicepublishing.models.netwrok.auth.User
import com.shadow.voicepublishing.models.netwrok.news.NewsResponse
import com.shadow.voicepublishing.network.ResultWrapper
import com.shadow.voicepublishing.repositories.ApiDataRepository
import com.shadow.voicepublishing.repositories.auth.abstraction.AuthRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val snakBarMessage = MutableLiveData<String>()
    val progressBar = MutableLiveData<Boolean>()
    val signUpResponse: LiveData<Boolean>
        get() = _signUpResponse
    private val _signUpResponse = MutableLiveData<Boolean>()
    val signInResponse: LiveData<Boolean>
        get() = _signInResponse
    private val _signInResponse = MutableLiveData<Boolean>()


    fun signUp(user: User) {
        showProgressBar(true)

        authRepository.signUp(user) { flag, message ->
            showProgressBar(false)
            _signUpResponse.value = flag
            showSnackBarMessage(message)


        }
    }

    fun signIn(email:String, password:String) {
        showProgressBar(true)

        authRepository.signIn(email,password) { flag, message ->
            showProgressBar(false)
            _signInResponse.value = flag
            showSnackBarMessage(message)


        }
    }

    fun setSignIn(flag:Boolean)
    {
        _signInResponse.value = flag
    }

    fun setSignUp(flag:Boolean)
    {
        _signUpResponse.value = flag
    }

    private fun showSnackBarMessage(message: String) {
        snakBarMessage.value = message
    }


    private fun showProgressBar(show: Boolean) {
        progressBar.value = show
    }

}