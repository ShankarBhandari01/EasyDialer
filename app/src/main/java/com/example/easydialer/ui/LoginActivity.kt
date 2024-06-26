package com.example.easydialer.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.R
import com.example.easydialer.databinding.ActivityLoginBinding
import com.example.easydialer.models.Login
import com.example.easydialer.models.LoginResponse
import com.example.easydialer.service.EasyDialerService
import com.example.easydialer.utils.ApiResultHandler
import com.example.easydialer.utils.Constants
import com.example.easydialer.utils.InAppUpdate
import com.example.easydialer.utils.SweetToast
import com.example.easydialer.utils.Utils
import com.example.easydialer.utils.Utils.checkDrawOverlayPermission
import com.example.easydialer.viewmodels.LoginViewModel
import com.example.easydialer.viewmodels.OfflineDatabaseViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val dataStoreViewModel by viewModels<OfflineDatabaseViewModel>()
    private val viewModel by viewModels<LoginViewModel>()
    lateinit var perms: Array<String>

    private lateinit var inAppUpdate: InAppUpdate
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode, resultCode, data)
    }


    override fun onResume() {
        super.onResume()
        inAppUpdate.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermissions()

        if (!checkDrawOverlayPermission(this)) {
            val intent = Intent(
                ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.packageName)
            )
            startActivity(intent)
        }


        inAppUpdate = InAppUpdate(this)


        binding.login.setOnClickListener {
            login()
        }
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        dataStoreViewModel.isFirstLaunch.observe(this) {
            it ?: return@observe
            if (it) {
                Toast.makeText(this, "First Time", LENGTH_SHORT).show()
            }

        }

        observers()
    }

    private fun observers() {
        viewModel.responseLogin.observe(this) { response ->
            val apiResultHandler = ApiResultHandler<LoginResponse>(this,
                onLoading = {
                    Utils.showProgressDialog("Logging in, Please wait ", this)
                },
                onSuccess = {
                    Utils.dismissProgressDialog()
                    if (it?.status == true) {
                        startActivity(MainActivity.getIntent(this, it))
                        startForegroundService()
                        finish()
                    } else {
                        Utils.showAlertDialog(this, it?.message)
                    }
                },
                onFailure = {
                    Utils.dismissProgressDialog()
                })
            apiResultHandler.handleApiResult(response)

        }
    }

    private fun login() {
        if (binding.username.text?.isBlank() == true) {
            SweetToast.error(this, "Please Enter UserName ! ")
            return
        } else if (binding.password.text?.isBlank() == true) {
            SweetToast.error(this, "Please Enter Password ! ")
            return
        }

        val login =
            Login(binding.username.text.toString(), binding.password.text.toString(), "AGENT")
        viewModel.login(login)
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
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            ACTION_MANAGE_OVERLAY_PERMISSION
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
                Manifest.permission.MANAGE_OWN_CALLS,
                Manifest.permission.READ_CALL_LOG,
                ACTION_MANAGE_OVERLAY_PERMISSION

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