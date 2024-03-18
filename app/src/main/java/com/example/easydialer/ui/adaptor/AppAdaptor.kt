package com.example.easydialer.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.databinding.RvCampaignListBinding
import com.example.easydialer.databinding.RvFollowUpStatusBinding
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.models.FollowUPStatus
import com.example.easydialer.ui.adaptor.viewholders.CampaignViewHolder
import com.example.easydialer.ui.adaptor.viewholders.FollowUpStatusHolder
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped // lifecycle is tired to activity lifecycle
class AppAdaptor<T>(private val onItemClick: (T) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<T> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CAMPAIGN_VIEW_TYPE_TYPE -> {
                CampaignViewHolder(
                    RvCampaignListBinding.inflate(inflater, parent, false),
                    onItemClick
                )
            }

            FOLLOW_UP_VIEW_TYPE_TYPE -> {
                FollowUpStatusHolder(
                    RvFollowUpStatusBinding.inflate(inflater, parent, false),
                    onItemClick
                )
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is CampaignResponseItem -> CAMPAIGN_VIEW_TYPE_TYPE
            is FollowUPStatus -> FOLLOW_UP_VIEW_TYPE_TYPE
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        when (holder) {
            is CampaignViewHolder<*> -> holder.bind(data as CampaignResponseItem)
            is FollowUpStatusHolder<*> -> holder.bind(data as FollowUPStatus)
        }
    }

    fun setData(dataList: ArrayList<T>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    companion object {
        private const val CAMPAIGN_VIEW_TYPE_TYPE = 0
        private const val FOLLOW_UP_VIEW_TYPE_TYPE = 1
    }
}