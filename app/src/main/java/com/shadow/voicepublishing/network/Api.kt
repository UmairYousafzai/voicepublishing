package com.shadow.voicepublishing.network

import com.shadow.voicepublishing.models.netwrok.news.NewsResponse
import com.shadow.voicepublishing.utils.CONSTANTS.POSTS
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    /****************************   category **********************/

    @GET(POSTS)
    suspend fun getNews(@Query("categories") category: String): NewsResponse


}