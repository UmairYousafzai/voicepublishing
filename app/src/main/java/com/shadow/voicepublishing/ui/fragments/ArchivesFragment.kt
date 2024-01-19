package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.shadow.voicepublishing.adapters.PDFNewsAdapter
import com.shadow.voicepublishing.databinding.FragmentArchivesBinding
import com.shadow.voicepublishing.databinding.FragmentSplashBinding
import com.shadow.voicepublishing.interfaces.PDFNewsClickListener
import com.shadow.voicepublishing.models.netwrok.news.PDFNews
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.ArchiveViewModel
import com.shadow.voicepublishing.view.models.DataStoreViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class ArchivesFragment : Fragment(), PDFNewsClickListener {

    val TAG= "ArchivesFragment"
    lateinit var binding: FragmentArchivesBinding
    private val viewModel: ArchiveViewModel by viewModels()
    private val loaderDialog by lazy { LoadingDialog(requireActivity()) }

    private lateinit var adapter :PDFNewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArchivesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        listenViewModelData()

    }

    private fun setupRecyclerView() {
        adapter = PDFNewsAdapter(this)
        binding.rvPdfNews.adapter = adapter

    }
    private fun listenViewModelData() {

        with(viewModel) {

            viewModel.getNews()

            progressBar.observe(viewLifecycleOwner) {
                if (it) loaderDialog.show()
                else loaderDialog.dismiss()
            }

            snakBarMessage.observe(viewLifecycleOwner) {
                showSnackBar(it)
            }

            newsResponse.observe(viewLifecycleOwner) {
                adapter.setOriginalList(it as ArrayList<PDFNews>)

//
            }
        }
    }

    override fun onNewsClicked(model: PDFNews, position: Int) {
        findNavController().navigate(ArchivesFragmentDirections.actionArchivesFragmentToPDFViewerFragment(model))
    }


}