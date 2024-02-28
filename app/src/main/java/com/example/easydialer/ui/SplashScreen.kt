package com.example.easydialer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.easydialer.databinding.ActivitySplashScreenBinding
import com.example.easydialer.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2000)
            startAnotherScreen()
        }

    }

    private fun startAnotherScreen() {
        startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
    }
}