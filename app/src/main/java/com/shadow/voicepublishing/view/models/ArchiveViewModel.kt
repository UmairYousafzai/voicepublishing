package com.shadow.voicepublishing.view.models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow.voicepublishing.models.netwrok.news.NewsResponse
import com.shadow.voicepublishing.models.netwrok.news.PDFNews
import com.shadow.voicepublishing.network.ResultWrapper
import com.shadow.voicepublishing.repositories.ApiDataRepository
import com.shadow.voicepublishing.repositories.archive.abstraction.ArchiveRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(private val repository: ArchiveRepository) :
    ViewModel() {

    val snakBarMessage = MutableLiveData<String>()
    val progressBar = MutableLiveData<Boolean>()
    val newsResponse: LiveData<List<PDFNews>>
        get() = _newsResponse
   private val _newsResponse= MutableLiveData<List<PDFNews>>()
   val pathResponse: LiveData<String>
        get() = _pathResponse
   private val _pathResponse= MutableLiveData<String>()

    fun getNews() {
        showProgressBar(true)
            repository.getAllPDFS(){ dataList, flag->
                showProgressBar(false)

                if (flag){
                    _newsResponse.value = dataList!!
                }else
                {
                    showSnackBarMessage("Loading failed")
                }

            }

    }
    fun downloadPDF(url:String,name:String) {
        showProgressBar(true)
            repository.downLoadPDF(url,name){ path, flag->
                showProgressBar(false)

                if (flag){
                    _pathResponse.value = path
                }else
                {
                    showSnackBarMessage("Downloading failed")
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