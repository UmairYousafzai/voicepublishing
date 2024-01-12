package com.shadow.voicepublishing.ui.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.shadow.voicepublishing.databinding.FragmentSignUpBinding
import com.shadow.voicepublishing.models.netwrok.auth.User
import com.shadow.voicepublishing.ui.dialogs.LoadingDialog
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.view.models.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()
    val loaderDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {

            signUp()
        }

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

            signUpResponse.observe(viewLifecycleOwner) {
                if (it) {
                    setSignUp(false)
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSubscriptionFragment())
                }
            }

        }
    }

    private fun signUp() {
        if (validateName() && validateEmail() && validatePassword()) {


            viewModel.signUp(
                User(
                    binding.etName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            )
        }
    }

    private fun validateName(): Boolean {
        val name = binding.etName.text.toString().trim()
        return if (name.isEmpty()) {
            binding.etNameLayout.error = "Name cannot be empty"
            false
        } else {
            true
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