package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.shadow.voicepublishing.databinding.FragmentLoginBinding
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.AuthViewModel
import com.shadow.voicepublishing.view.models.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    val loaderDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnListeners()
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

            signInResponse.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSubscriptionFragment())
                    setSignIn(false)
                }
            }

        }
    }

    private fun btnListeners() {

        binding.btnLogin.setOnClickListener {

            signIn()
//            findNavController().navigate(LoginFragmentDirections.loginFragmentToCategoryFragment())
        }

        binding.btnForgotPassword.setOnClickListener {

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        binding.tvNewUser.setOnClickListener {

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

    private fun signIn() {

        if (validateEmail() && validatePassword()) {

            viewModel.signIn(binding.etEmail.text.toString(), binding.etPassword.text.toString())
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

    private fun validatePassword(): Boolean {
        val password = binding.etPassword.text.toString().trim()
        return if (password.length < 6) {
            binding.etPasswordLayout.error = "Password should be at least 6 characters long"
            false
        } else {
            true
        }
    }


}