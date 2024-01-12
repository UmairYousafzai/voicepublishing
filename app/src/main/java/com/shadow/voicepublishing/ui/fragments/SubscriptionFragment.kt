package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.shadow.voicepublishing.databinding.FragmentSubscriptionBinding
import com.shadow.voicepublishing.utils.IS_SUBSCRIBE
import com.shadow.voicepublishing.utils.IS__SUBSCRIBE
import com.shadow.voicepublishing.view.models.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SubscriptionFragment : Fragment() {

    lateinit var binding: FragmentSubscriptionBinding
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnListeners()

    }

    private fun btnListeners() {

        binding.btnSubscribe.setOnClickListener {
            dataStoreViewModel.put(IS_SUBSCRIBE,true)
            findNavController().navigate(SubscriptionFragmentDirections.actionSubscriptionFragmentToCategoryFragment())
        }
    }


}