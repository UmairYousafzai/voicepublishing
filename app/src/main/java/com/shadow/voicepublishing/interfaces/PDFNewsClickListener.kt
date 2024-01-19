package com.shadow.voicepublishing.interfaces

import com.shadow.voicepublishing.models.netwrok.news.News
import com.shadow.voicepublishing.models.netwrok.news.PDFNews


interface PDFNewsClickListener {

    fun  onNewsClicked(model: PDFNews, position:Int)
}