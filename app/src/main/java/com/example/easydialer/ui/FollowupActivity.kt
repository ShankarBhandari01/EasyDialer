package com.example.easydialer.ui

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.easydialer.databinding.ActivityFollowupBinding
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.models.MobileList
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FollowupActivity : AppCompatActivity() {
    var currentIndex = 0
    private val binding by lazy { ActivityFollowupBinding.inflate(layoutInflater) }

    companion object {
        private lateinit var mobileList: MobileList
        fun getIntent(context: Context, mobileList: MobileList): Intent {
            this.mobileList = mobileList
            return Intent(context, FollowupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.back.setOnClickListener { onBackPressed() }

        with(binding) {
            toolbar.title.text = "Call FollowUp"
            binding.phone.setText(mobileList[currentIndex].mobile)

            datetime.editText?.setText(getCurrentDateTimeWithAMPM())

            datetime.setEndIconOnClickListener {
                showDateTimePickerDialog()
            }

            phoneLayout.setEndIconOnClickListener {
                callnow.performClick()
            }

            callnow.setOnClickListener {

                runWithPermissions(Manifest.permission.CALL_PHONE) {
                    val intent = Intent(
                        Intent.ACTION_CALL,
                        Uri.parse(
                            "tel:" + binding.phone.text.toString().trim()
                        )
                    )

                    startActivity(intent)
                }
                Toast.makeText(this@FollowupActivity, "Call starting...", Toast.LENGTH_SHORT).show()
            }

            stopnext.setOnClickListener {
                handleNextNumber()
            }
        }

    }

    private fun handleNextNumber() {
        if (currentIndex < mobileList.size - 1) {
            currentIndex++
        } else {
            Toast.makeText(this, "Number is not available ", Toast.LENGTH_SHORT).show()
        }
        binding.phone.setText(mobileList[currentIndex].mobile)
    }

    private fun getCurrentDateTimeWithAMPM(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                showTimePickerDialog(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(calendar: Calendar) {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val selectedDateTime = formatDateTime(calendar)
                binding.datetime.editText?.setText(selectedDateTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // 24-hour format
        )

        timePickerDialog.show()
    }


    private fun formatDateTime(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

}