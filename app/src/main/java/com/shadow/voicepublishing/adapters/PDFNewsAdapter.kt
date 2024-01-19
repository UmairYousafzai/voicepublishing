package com.shadow.voicepublishing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shadow.voicepublishing.R
import com.shadow.voicepublishing.databinding.RvItemPdfBinding
import com.shadow.voicepublishing.interfaces.NewsClickListener
import com.shadow.voicepublishing.databinding.RvNewsItemBinding
import com.shadow.voicepublishing.interfaces.PDFNewsClickListener
import com.shadow.voicepublishing.models.netwrok.news.News
import com.shadow.voicepublishing.models.netwrok.news.PDFNews
import com.shadow.voicepublishing.utils.gone
import com.shadow.voicepublishing.utils.loadImage
import com.shadow.voicepublishing.utils.visible
import com.shadow.voicepublishing.view.holders.NewsViewHolder
import com.shadow.voicepublishing.view.holders.PDFNewsViewHolder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


import java.util.*
import kotlin.collections.ArrayList

class PDFNewsAdapter(
    val listener: PDFNewsClickListener
) : RecyclerView.Adapter<PDFNewsViewHolder>() {


    val originalList = ArrayList<PDFNews>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFNewsViewHolder =
        PDFNewsViewHolder(
            RvItemPdfBinding.inflate(LayoutInflater.from(parent.context))
        )


    override fun onBindViewHolder(holder: PDFNewsViewHolder, position: Int) {
        val model = originalList[position]
        holder.binding.tvFileName.text = model.name
        holder.binding.tvFileName.setOnClickListener {
                listener.onNewsClicked(model,position)

        }


    }


    fun setOriginalList(list: ArrayList<PDFNews>) {
        originalList.clear()
        originalList.addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return originalList.size
    }
}