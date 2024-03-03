package com.example.easydialer.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.R
import com.example.easydialer.viewmodels.OfflineDatabaseViewModel
import com.example.easydialer.databinding.ActivityLoginBinding
import com.example.easydialer.service.EasyDialerService
import com.example.easydialer.utils.Constants
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val dataStoreViewModel by viewModels<OfflineDatabaseViewModel>()
    lateinit var perms: Array<String>
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermissions()
        binding.login.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            startForegroundService()
            finish()
        }
        dataStoreViewModel.isFirstLaunch.observe(this) {
            it ?: return@observe
            if (it) {
                Toast.makeText(this, "First Time", LENGTH_SHORT).show()
            }

        }
    }


    private fun startForegroundService() {
        val serviceIntent = Intent(this, EasyDialerService::class.java)
        CoroutineScope(IO).launch {
            if (SDK_INT >= O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE)
    private fun checkPermissions() {
        perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        )
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perms = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.MANAGE_OWN_CALLS
            )
        }
        if (EasyPermissions.hasPermissions(this, *perms)) {

        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.all_permission_req),
                Constants.REQUEST_CODE,
                *perms
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        TODO("Not yet implemented")
    }

}