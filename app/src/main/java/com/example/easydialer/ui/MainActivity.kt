package com.example.easydialer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import com.example.easydialer.R
import com.example.easydialer.viewmodels.OfflineDatabaseViewModel
import com.example.easydialer.databinding.ActivityMainBinding
import com.example.easydialer.models.DashboardMenu
import com.example.easydialer.models.FollowUPStatus
import com.example.easydialer.ui.adaptor.AppAdaptor
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel by viewModels<OfflineDatabaseViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var appAdaptor: AppAdaptor<DashboardMenu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataStoreViewModel.setFirstLaunch(false)
        init()
        observes()
    }

    private fun init() {
        appAdaptor = AppAdaptor(this) {
            startActivity(Intent(this@MainActivity, TelDialerActivity::class.java))

        }
        binding.list.apply { adapter = appAdaptor }
        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(
            this,
            R.anim.layout_animation_fall_down
        )
        binding.list.layoutAnimation = animation
    }

    private fun observes() {
        val data = ArrayList<DashboardMenu>()
        data.add(DashboardMenu("Telecom"))
        data.add(DashboardMenu("Report"))
        data.add(DashboardMenu("Users"))
        data.add(DashboardMenu("Report"))

        appAdaptor.setData(data)
    }


}