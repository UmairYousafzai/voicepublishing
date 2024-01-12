package com.shadow.voicepublishing.models.netwrok.news

import java.io.Serializable

data class News(
    val content: Content = Content(),
    val date: String="",
    val id: Int=0,
    val title: Title = Title(),
    val link:String="",
    val yoast_head_json: YoastHeadJson = YoastHeadJson()
):Serializable