package com.shadow.voicepublishing.view.models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow.voicepublishing.models.netwrok.news.NewsResponse
import com.shadow.voicepublishing.network.ResultWrapper
import com.shadow.voicepublishing.repositories.ApiDataRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    ViewModel() {

    val snakBarMessage = MutableLiveData<String>()
    val progressBar = MutableLiveData<Boolean>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse
   private val _newsResponse= MutableLiveData<NewsResponse>()

    fun getNews(category: String) {
        showProgressBar(true)
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.getNews(category)
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            _newsResponse.value = response.value!!
                        }

                        else -> handleErrorType(response)
                    }
                }
        }
    }

    private fun showSnackBarMessage(message: String) {
        snakBarMessage.value = message
    }


    private fun showProgressBar(show: Boolean) {
        progressBar.value = show
    }

    fun handleErrorType(error: ResultWrapper<Any>) {
        showProgressBar(false)
        when (error) {
            is ResultWrapper.NetworkError ->
                showSnackBarMessage("Internet not available")

            is ResultWrapper.GenericError ->
                showSnackBarMessage("" + error.error?.message)

            else -> {

            }
        }
    }
}