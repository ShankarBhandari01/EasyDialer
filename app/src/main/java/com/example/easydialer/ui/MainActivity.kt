package com.example.easydialer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.BR
import com.example.easydialer.R
import com.example.easydialer.databinding.ActivityMainBinding
import com.example.easydialer.models.LoginResponse
import com.example.easydialer.models.Menus
import com.example.easydialer.ui.adaptor.AppAdaptor
import com.example.easydialer.utils.MenuType
import com.example.easydialer.utils.SweetToast
import com.example.easydialer.viewmodels.OfflineDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel by viewModels<OfflineDatabaseViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var appAdaptor: AppAdaptor<Menus>

    companion object {
        lateinit var loginResponse: LoginResponse
        fun getIntent(context: Context, loginResponse: LoginResponse): Intent {
            this.loginResponse = loginResponse
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataStoreViewModel.setFirstLaunch(false)
        binding.setVariable(BR.agent, loginResponse.data)
        init()
        appAdaptor.setData(loginResponse.menus)
    }

    private fun init() {
        appAdaptor = AppAdaptor(this) {
            when (MenuType.fromDisplayName(it.name)) {
                MenuType.TELE_CALLER -> {
                    startActivity(Intent(this@MainActivity, TelDialerActivity::class.java))
                }

                MenuType.DATA_ENTRY -> {
                    SweetToast.info(this, "Data Entry")
                }

                MenuType.ATTENDANCE -> {
                    SweetToast.info(this, "Attendance")
                }

                MenuType.TICKET_SYSTEM -> {
                    SweetToast.info(this, "Ticket System")
                }

                MenuType.CRM -> {
                    SweetToast.info(this, "CRM")
                }

                MenuType.LEAVE -> {
                    SweetToast.info(this, "Leave")
                }

                else -> {
                    SweetToast.info(this, "Menu does not exits yet")
                }
            }

        }
        binding.list.apply { adapter = appAdaptor }
        val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        binding.list.layoutAnimation = animation
    }


}