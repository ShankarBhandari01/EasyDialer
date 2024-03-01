package com.example.easydialer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.easydialer.viewmodels.OfflineDatabaseViewModel
import com.example.easydialer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel by viewModels<OfflineDatabaseViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataStoreViewModel.setFirstLaunch(false)
        binding.telecom.setOnClickListener {
            startActivity(Intent(this@MainActivity, TelDialerActivity::class.java))
        }
    }


}