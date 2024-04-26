package com.example.easydialer.ui.adaptor.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.BR
import com.example.easydialer.databinding.RvCampaignListBinding
import com.example.easydialer.models.CampaignResponseItem

class CampaignViewHolder<T>(
    private val binding: RvCampaignListBinding,
    private val onItemClick: (T) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(campaign: CampaignResponseItem) {
        binding.setVariable(BR.campaign, campaign)
        binding.executePendingBindings()
        binding.campaignCardView.setOnClickListener {
            onItemClick(campaign as T)
        }
    }
}