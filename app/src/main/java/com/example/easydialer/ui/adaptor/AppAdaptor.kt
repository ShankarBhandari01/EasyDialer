package com.example.easydialer.ui.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easydialer.databinding.RvCampaignListBinding
import com.example.easydialer.databinding.RvDashboardMenuBinding
import com.example.easydialer.databinding.RvFollowUpStatusBinding
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.models.FollowUPStatus
import com.example.easydialer.models.Menus
import com.example.easydialer.ui.adaptor.viewholders.CampaignViewHolder
import com.example.easydialer.ui.adaptor.viewholders.DashboardMenuHolder
import com.example.easydialer.ui.adaptor.viewholders.FollowUpStatusHolder
import com.example.easydialer.utils.Constants.Companion.CAMPAIGN_VIEW_TYPE_TYPE
import com.example.easydialer.utils.Constants.Companion.DASHBOARD_MENU_VIEW_TYPE
import com.example.easydialer.utils.Constants.Companion.FOLLOW_UP_VIEW_TYPE_TYPE
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped // lifecycle is tired to activity lifecycle
class AppAdaptor<T>(var context: Context, private val onItemClick: (T) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                    onItemClick,
                    context
                )
            }

            DASHBOARD_MENU_VIEW_TYPE -> {
                DashboardMenuHolder(
                    RvDashboardMenuBinding.inflate(inflater, parent, false),
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
            is Menus -> DASHBOARD_MENU_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        when (holder) {
            is CampaignViewHolder<*> -> holder.bind(data as CampaignResponseItem)
            is FollowUpStatusHolder<*> -> holder.bind(data as FollowUPStatus, position)
            is DashboardMenuHolder<*> -> holder.bind(data as Menus)
        }
    }

    fun setData(dataList: ArrayList<T>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}