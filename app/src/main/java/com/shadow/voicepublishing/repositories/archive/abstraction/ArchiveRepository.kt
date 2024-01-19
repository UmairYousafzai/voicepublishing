package com.shadow.voicepublishing.repositories.archive.abstraction

import com.shadow.voicepublishing.models.netwrok.news.PDFNews

interface ArchiveRepository {

     fun getAllPDFS(onSuccess:(List<PDFNews>?,Boolean) -> Unit)
     fun downLoadPDF(url:String,name:String,onSuccess:(String,Boolean) -> Unit)
}