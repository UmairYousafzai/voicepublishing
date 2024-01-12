package com.shadow.voicepublishing.repositories



import com.shadow.voicepublishing.models.netwrok.news.NewsResponse
import com.shadow.voicepublishing.network.Api
import com.shadow.voicepublishing.network.ResultWrapper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class ApiDataRepository @Inject constructor( ) {
    @Inject
    lateinit var retrofit: Retrofit
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getNews(category: String): ResultWrapper<NewsResponse> {
        return safeApiCall(dispatcher) {
            retrofit.create(Api::class.java).getNews(category)
        }
    }

}