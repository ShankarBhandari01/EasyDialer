package com.example.easydialer.ui.adaptor.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.BR
import com.example.easydialer.databinding.RvFollowUpStatusBinding
import com.example.easydialer.models.FollowUPStatus

class FollowUpStatusHolder<T>(
    private val binding: RvFollowUpStatusBinding,
    private val onItemClick: (data: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(status: FollowUPStatus) {
        binding.setVariable(BR.status, status)
        binding.executePendingBindings()
        binding.btnLeft.setOnClickListener {
            onItemClick(status as T)
        }
        binding.btnRight.setOnClickListener {
            onItemClick(status as T)
        }
    }

}