package com.example.easydialer.ui.adaptor.viewholders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.BR
import com.example.easydialer.databinding.RvFollowUpStatusBinding
import com.example.easydialer.models.FollowUPStatus

class FollowUpStatusHolder<T>(
    private val binding: RvFollowUpStatusBinding,
    private val onItemClick: (data: T) -> Unit,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(status: FollowUPStatus, postion: Int) {
        binding.setVariable(BR.status, status)
        binding.executePendingBindings()
        if ((postion + 1) % 2 == 0) {
            binding.btnLeft.backgroundTintList =
                ContextCompat.getColorStateList(context, android.R.color.holo_red_dark)
        }
        binding.btnLeft.setOnClickListener {
            onItemClick(status as T)
        }
    }

}