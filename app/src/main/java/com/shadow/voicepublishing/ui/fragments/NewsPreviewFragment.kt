package com.shadow.voicepublishing.ui.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shadow.voicepublishing.databinding.FragmentArticlePreviewBinding


import com.shadow.voicepublishing.utils.loadImage
import com.shadow.voicepublishing.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


@AndroidEntryPoint
class NewsPreviewFragment : Fragment() {

    lateinit var binding: FragmentArticlePreviewBinding
    private val model by lazy { arguments?.let { NewsPreviewFragmentArgs.fromBundle(it).news } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArticlePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.model = model
        setUpContent()

        setUpWebView()
        btnListeners()

    }

    private fun btnListeners() {

        binding.btnShare.setOnClickListener {
            model?.let { it1 -> shareArticle(it1.link) }
        }
    }


    private fun setUpContent() {

        val document: Document = Jsoup.parse(model?.content?.rendered.toString())

        var content = ""

        val paragraphs: Elements = document.select("p")
        for (paragraph: Element in paragraphs) {
            content = content + paragraph.text()+ "\n"
        }

        val figures: Elements = document.select("figure")
        val styleTags: List<Element> = document.select("style")

        if (figures.isNotEmpty()) {
            val image = figures[0].selectFirst("img")?.attr("src")

            binding.ivPreview.loadImage(image)
        }
        binding.tvDescription.text = content
//        figures.remove()
//        if (styleTags.isNotEmpty()) {
//            val lastStyleTag: Element = styleTags.last()
//            lastStyleTag.remove()
//
//        }
//        binding.tvDescription.text = Html.fromHtml(document.body().html(), Html.FROM_HTML_MODE_COMPACT)


    }

    fun shareArticle(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain" // MIME type for plain text
        intent.putExtra(Intent.EXTRA_TEXT, text)

        val chooser = Intent.createChooser(intent, "Share via")

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireActivity().startActivity(chooser)
        } else {
           showSnackBar("No app found to share the text")
        }
    }


    private fun setUpWebView() {

//        binding.webView.settings.apply {
//            javaScriptEnabled = true
//            loadWithOverviewMode = true
//            useWideViewPort = true
//            domStorageEnabled = true
////            builtInZoomControls = true
////            displayZoomControls = false
//        }
//
//        binding.webView.webViewClient = WebViewClient()
//
//        binding.webView.loadDataWithBaseURL(null,
//            model?.content?.rendered ?: "", "text/html", "UTF-8", null)
//        Handler(Looper.getMainLooper()).postDelayed({     binding.webView.zoomIn()
//            binding.webView.zoomIn() },200)


    }


}