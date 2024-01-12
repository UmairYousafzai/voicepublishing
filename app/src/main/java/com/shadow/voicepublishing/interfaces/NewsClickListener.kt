package com.shadow.voicepublishing.interfaces

import com.shadow.voicepublishing.models.netwrok.news.News


interface NewsClickListener {

    fun  onNewsClicked(model: News, position:Int)
    fun  onSubscribeClicked()
}