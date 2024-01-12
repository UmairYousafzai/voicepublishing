package com.shadow.voicepublishing.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.v2.DbxClientV2


import com.google.android.material.tabs.TabLayout
import com.shadow.voicepublishing.adapters.NewsAdapter
import com.shadow.voicepublishing.databinding.CustomTabsBinding
import com.shadow.voicepublishing.databinding.FragmentCatergoryBinding
import com.shadow.voicepublishing.interfaces.NewsClickListener
import com.shadow.voicepublishing.models.netwrok.news.News
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.CategoryViewModel
import com.shadow.voicepublishing.view.models.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class CategoryFragment : Fragment(), NewsClickListener {

    lateinit var binding: FragmentCatergoryBinding
    lateinit var adapter: NewsAdapter
    private val viewModel: CategoryViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    val loaderDialog by lazy { LoadingDialog(requireActivity()) }
    private var isFragmentFreshlyCreated = true

    private var selectedTabIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCatergoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        setUpTabs()
//
//        setUpAdapter()
//        listenViewModelData()
//        getUserSubscriptionStatus()

        lifecycleScope.launch(Dispatchers.IO) {
            val config = DbxRequestConfig.newBuilder("Testing46").build()
            val client = DbxClientV2(config, "sl.BteNXmPW-fNRLpq6FebMsCqYNbzA5oCy4yuNvw7FoBEZf1NuQjJ_UPqaYqIuRISqXSI5kum4np98YHOcqjnwxKMZOStBoB_ElBLW_led28y_JvJb7kq9_j53DqOl-X8bEys6OQwdQYpCeyTOAvFKnTc-")
            val filePath = "/pdfs/123456.pdf"

            val destinationPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "123456.pdf"
            try {
                val outputStream = FileOutputStream(File(destinationPath))
                client.files().download(filePath).download(outputStream)
                outputStream.close()

                // Open the downloaded PDF file using an Intent
                val pdfFile = File(destinationPath)
                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider",
                    pdfFile
                )

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)

            } catch (e: DbxException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    private fun getUserSubscriptionStatus() {

        lifecycleScope.launch {
            dataStoreViewModel.isSubscribeStatus.collect{

                adapter.isSubscribe = it
                adapter.notifyDataSetChanged()
            }
        }

    }


    private fun listenViewModelData() {

        with(viewModel) {

            viewModel.getNews("5")

            progressBar.observe(viewLifecycleOwner) {
                if (it) loaderDialog.show()
                else loaderDialog.dismiss()
            }

            snakBarMessage.observe(viewLifecycleOwner) {
                showSnackBar(it)
            }

            newsResponse.observe(viewLifecycleOwner) {
                isFragmentFreshlyCreated = false
                adapter.setOriginalList(it)

//                val document: Document = Jsoup.parse(it[0].content.rendered)
//
//                // Extract paragraphs
//                val paragraphs: Elements = document.select("p")
//                for (paragraph: Element in paragraphs) {
//                    println(paragraph.text())
//                }
//
//                // Extract images
//                val figures: Elements = document.select("figure")
//                for (figure: Element in figures) {
//                    val img: Element? = figure.selectFirst("img")
//                    val imageUrl: String? = img?.attr("src")
//                    println("Image URL: $imageUrl")
//
//                    val caption: Element? = figure.selectFirst("figcaption")
//                    val captionText: String? = caption?.text()
//                    println("Image Caption: $captionText")
//                }
            }
        }
    }

    private fun setUpAdapter() {

        adapter = NewsAdapter(this)
        binding.rvNews.adapter = adapter
    }

    private fun setUpTabs() {

        var tab = binding.tabLayout.newTab().setCustomView(createCustomTabView("News", "5"))
        tab.tag = "5"

        binding.tabLayout.addTab(
            tab
        )

        tab = binding.tabLayout.newTab().setCustomView(createCustomTabView("Editorial", "4"))
        tab.tag = "4"

        binding.tabLayout.addTab(
            tab
        )

        tab = binding.tabLayout.newTab().setCustomView(createCustomTabView("Opinion", "15"))
        tab.tag = "15"

        binding.tabLayout.addTab(
            tab
        )

        tab = binding.tabLayout.newTab().setCustomView(createCustomTabView("Sports", "10"))
        tab.tag = "10"

        binding.tabLayout.addTab(
            tab
        )

        tab = binding.tabLayout.newTab().setCustomView(createCustomTabView("Features", "6"))
        tab.tag = "6"

        binding.tabLayout.addTab(
            tab
        )

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getNews(tab?.tag as String)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun createCustomTabView(tabText: String, tag: String): View {
        val binding = CustomTabsBinding.inflate(layoutInflater)
        binding.title.text = tabText
        binding.title.tag = tag

//        binding.root.setOnClickListener {
//            isFragmentFreshlyCreated = true
//
//            viewModel.getNews(binding.title.tag as String)
//
//        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onNewsClicked(model: News, position: Int) {

        findNavController().navigate(
            CategoryFragmentDirections.categoryFragmentToNewsPreviewFragment(
                model
            )
        )

    }

    override fun onSubscribeClicked() {
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToLoginFragment())
    }


}