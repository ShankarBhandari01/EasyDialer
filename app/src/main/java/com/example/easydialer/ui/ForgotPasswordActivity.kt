package com.example.easydialer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easydialer.databinding.ActivityForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}