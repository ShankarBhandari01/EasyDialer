package com.example.easydialer.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.data.local.AppViewModel
import com.example.easydialer.ui.MainActivity
import com.example.easydialer.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val dataStoreViewModel by viewModels<AppViewModel>()
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        dataStoreViewModel.isFirstLaunch.observe(this) {
            it ?: return@observe
            if (it) {
                Toast.makeText(this, "First Time", Toast.LENGTH_SHORT).show()
            }

        }
    }
}