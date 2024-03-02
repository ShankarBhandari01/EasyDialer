package com.example.easydialer.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.databinding.RvCampaignListBinding
import com.example.easydialer.models.CampaignResponseItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CampaignAdaptor(private val onItemClick: (campaign: CampaignResponseItem) -> Unit) :
    RecyclerView.Adapter<CampaignAdaptor.MyHolder>() {
    private var campaignItemList: ArrayList<CampaignResponseItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            RvCampaignListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val campaign = campaignItemList[position]
        holder.binding.campaign = campaign
        holder.binding.campaignCardView.setOnClickListener {
            onItemClick(campaign)
        }

    }

    fun setCampaign(campaignList: ArrayList<CampaignResponseItem>) {
        campaignItemList.addAll(campaignList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return campaignItemList.size
    }

    inner class MyHolder(val binding: RvCampaignListBinding) : RecyclerView.ViewHolder(binding.root)


}