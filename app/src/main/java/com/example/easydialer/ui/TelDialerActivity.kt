package com.example.easydialer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.easydialer.data.ApiResponseData
import com.example.easydialer.data.ApiResultHandler
import com.example.easydialer.databinding.ActivityTeleDialerBinding
import com.example.easydialer.login.LoginViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TelDialerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTeleDialerBinding.inflate(layoutInflater)
    }
    private val mainViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.back.setOnClickListener {
           onBackPressedDispatcher.onBackPressed()
        }

        binding.toolbar.title.text = "Select Campaign"

        binding.first.setOnClickListener {
            startActivity(Intent(this@TelDialerActivity, CampaginDetailsActivity::class.java))
        }
        init()
        getProducts()
        observeProductData()
    }
    private fun init() {
        try {
//            productListAdapter = ProductListAdapter()
//            activityMainBinding.list.apply { adapter = productListAdapter }
//            activityMainBinding.swipeRefreshLayout.setOnRefreshListener { getProducts() }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
    private fun observeProductData() {
        try {
            mainViewModel.response.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<ApiResponseData>(this@TelDialerActivity,
                    onLoading = {
                        binding.progress.visibility = View.VISIBLE
                    },
                    onSuccess = { data ->
                        binding.progress.visibility = View.GONE
//                        data?.Data?.marketList?.let { productListAdapter.setProducts(it) }
//                        binding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        binding.progress.visibility = View.GONE
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun getProducts() {
        try {
            /*create your json for post request*/
            var jsonObject = JsonObject().apply {

            }
            mainViewModel.getProductsList(jsonObject)
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}