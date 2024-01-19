package com.shadow.voicepublishing.repositories.archive.implementation

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.shadow.voicepublishing.models.netwrok.news.PDFNews

import com.shadow.voicepublishing.repositories.archive.abstraction.ArchiveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ArchiveRepoImpl @Inject constructor( val context: Context): ArchiveRepository {
    val TAG= "ArchiveRepoImpl"
    val token = "sl.Bt5LVNvukNgXj6OZ1uu0S-LBe8_k3QHk9p84Vh1GLMaFXDD6EP8vTrFAXvCskDDiYCC0enpgdFKOMA_oJkO62YGbX3ytEO3i42fdJsgJucHm69JYRka3ZiBXnjhS9-hVfs-QXwesBWOseIkJF1e7B6w"
    override  fun getAllPDFS(onSuccess: (List<PDFNews>?, Boolean) -> Unit) {

            val config = DbxRequestConfig.newBuilder("Testing46").build()
            val client = DbxClientV2(config,token )
            val pdfList= arrayListOf<PDFNews>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val  result = client.files().listFolder("")
                Log.d(TAG, "getAllPDFS: ${result.toStringMultiline()}")
                for (metadata in result.entries) {
                    if (metadata is FileMetadata && metadata.name.endsWith(".pdf")) {
                        // Handle each PDF file
                        println(metadata.name)
                        pdfList.add(PDFNews(metadata.name,metadata.pathDisplay))

                    }
                }
                withContext(Dispatchers.Main){
                    onSuccess.invoke(pdfList,true)

                }

            } catch (e: DbxException) {
                withContext(Dispatchers.Main){
                    onSuccess.invoke(null,true)
                }

                e.printStackTrace()
            }

        }


    }

    override fun downLoadPDF(url: String,name:String, onSuccess: (String, Boolean) -> Unit) {


        CoroutineScope(Dispatchers.IO).launch {
            val config = DbxRequestConfig.newBuilder("Testing46").build()
            val client = DbxClientV2(config, token)

            val destinationPath = context.cacheDir
                .toString() + "/$name"
            try {
                val outputStream = FileOutputStream(File(destinationPath))
                client.files().download(url).download(outputStream)
                outputStream.close()

                withContext(Dispatchers.Main){
                    onSuccess.invoke(destinationPath,true)

                }
            } catch (e: DbxException) {


                e.printStackTrace()
            } catch (e: IOException) {
                withContext(Dispatchers.Main){
                    onSuccess.invoke(e.message.toString(),false)
                }
                e.printStackTrace()
            }
        }
    }
}