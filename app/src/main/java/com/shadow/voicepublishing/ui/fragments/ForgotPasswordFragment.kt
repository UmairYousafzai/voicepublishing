package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shadow.voicepublishing.databinding.FragmentForgotPasswordBinding
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()
    val loaderDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnListener()
        viewModelListeners()

    }

    private fun viewModelListeners() {

        with(viewModel) {

            progressBar.observe(viewLifecycleOwner) {
                if (it) loaderDialog.show()
                else loaderDialog.dismiss()
            }

            snakBarMessage.observe(viewLifecycleOwner) {
                showSnackBar(it)
            }

            resetPasswordResponse.observe(viewLifecycleOwner) {
                if (it) {
                 binding.etEmail.setText("")
                }
            }

        }
    }

    private fun btnListener() {

        binding.btnNext.setOnClickListener {
            if (validateEmail()){
                viewModel.resetPassword(binding.etEmail.text.toString())
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailLayout.error = "Enter a valid email address"
            false
        } else {
            true
        }
    }
}