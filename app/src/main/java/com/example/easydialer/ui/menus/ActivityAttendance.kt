package com.example.easydialer.ui.menus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.databinding.ActivityAttendanceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAttendance : AppCompatActivity() {
    private val binding by lazy {
        ActivityAttendanceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.title.text = "Attendance"
    }
}