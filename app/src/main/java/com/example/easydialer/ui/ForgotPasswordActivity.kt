package com.example.easydialer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.easydialer.databinding.ActivityForgotPasswordBinding
import com.example.easydialer.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            var username = username.text.toString()
            var oldPassword = password.text.toString()
            var newPassword = newpassword.text.toString()
            var confirmPassword = confirmpassword.text.toString()
            btnUpdatePassword.setOnClickListener {
                doPasswordChange()
            }
        }
    }


    private fun doPasswordChange() {
        viewModel.updatePassword()
    }
}