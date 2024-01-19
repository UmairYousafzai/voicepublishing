package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shadow.voicepublishing.databinding.FragmentPdfViewerBinding
import com.shadow.voicepublishing.databinding.FragmentSplashBinding
import com.shadow.voicepublishing.models.netwrok.news.PDFNews
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.ArchiveViewModel

import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class PDFViewerFragment : Fragment() {

    lateinit var binding: FragmentPdfViewerBinding
    private val viewModel: ArchiveViewModel by viewModels()
    private val loaderDialog by lazy { LoadingDialog(requireActivity()) }
    private val model by lazy {
        PDFViewerFragmentArgs.fromBundle(requireArguments()).pdf
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listenViewModelData()
    }

    private fun listenViewModelData() {

        with(viewModel) {

            viewModel.downloadPDF(model.path,model.name)

            progressBar.observe(viewLifecycleOwner) {
                if (it) loaderDialog.show()
                else loaderDialog.dismiss()
            }

            snakBarMessage.observe(viewLifecycleOwner) {
                showSnackBar(it)
            }

            pathResponse.observe(viewLifecycleOwner) {

                binding.pdfView.fromFile(File(it)).show()
            }
        }
    }



}