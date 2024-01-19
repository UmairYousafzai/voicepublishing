package com.shadow.voicepublishing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shadow.voicepublishing.R
import com.shadow.voicepublishing.interfaces.NewsClickListener
import com.shadow.voicepublishing.databinding.RvNewsItemBinding
import com.shadow.voicepublishing.models.netwrok.news.News
import com.shadow.voicepublishing.utils.gone
import com.shadow.voicepublishing.utils.loadImage
import com.shadow.voicepublishing.utils.visible
import com.shadow.voicepublishing.view.holders.NewsViewHolder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    val listener: NewsClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {


    val originalList = ArrayList<News>()
    var isSubscribe = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            RvNewsItemBinding.inflate(LayoutInflater.from(parent.context))
        )


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val model = originalList[position]
        holder.binding.model = model

        if (isSubscribe) {
            holder.binding.viewSubscription.gone()
            holder.binding.tvSubscription.gone()
            holder.binding.btnSubscribe.gone()
        }else
        {
            holder.binding.viewSubscription.visible()
            holder.binding.tvSubscription.visible()
            holder.binding.btnSubscribe.visible()
        }
        val document: Document = Jsoup.parse(model.content.rendered)
        val figures: Elements = document.select("figure")
        if (figures.isNotEmpty()) {
            val image = figures[0].selectFirst("img")?.attr("src")
            holder.binding.ivThumbnail.loadImage(image)
        }else
        {
            holder.binding.ivThumbnail.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context,R.drawable.voice_digital_logo))
        }

        val paragraphs: Elements = document.select("p")
        if (paragraphs.isNotEmpty()){
            holder.binding.tvDescription.text = paragraphs[0].text()
        }else
        {
            holder.binding.tvDescription.text=""
        }
        holder.binding.parent.setOnClickListener {
            if (isSubscribe) {
                listener.onNewsClicked(model,position)
            }
        }

        holder.binding.btnSubscribe.setOnClickListener {
            listener.onSubscribeClicked()
        }
    }


    fun setOriginalList(list: ArrayList<News>) {
        originalList.clear()
        originalList.addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return originalList.size
    }
}