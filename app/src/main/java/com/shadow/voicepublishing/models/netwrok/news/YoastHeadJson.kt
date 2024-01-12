package com.shadow.voicepublishing.models.netwrok.news

import com.shadow.voicepublishing.models.netwrok.news.OgImage
import java.io.Serializable

data class YoastHeadJson(
    val article_modified_time: String="",
    val article_published_time: String="",
    val article_publisher: String="",
    val author: String="",
    val description: String="",
    val og_description: String="",
    val og_image: List<OgImage> = arrayListOf(),
):Serializable