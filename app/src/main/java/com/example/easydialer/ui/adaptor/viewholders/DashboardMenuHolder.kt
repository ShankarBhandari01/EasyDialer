package com.example.easydialer.ui.adaptor.viewholders

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.databinding.RvDashboardMenuBinding
import com.example.easydialer.models.Menus

class DashboardMenuHolder<T>(
    var binding: RvDashboardMenuBinding,
    private val onItemClick: (data: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(menu: Menus) {
        binding.setVariable(BR.menu, menu)
        binding.executePendingBindings()
        binding.telecom.setOnClickListener { onItemClick(menu as T) }
    }

}