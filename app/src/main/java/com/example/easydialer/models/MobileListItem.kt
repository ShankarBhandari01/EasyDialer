package com.example.easydialer.models

import java.io.Serializable

data class MobileListItem(
    val campaign_id: String,
    val contacted: Int,
    val contacted_at: Any,
    val created_at: String,
    val description: String,
    val dialed: Int,
    val dialed_at: Any,
    val filename: Any,
    val id: Int,
    val mobile: String,
    val not_contacted: Int,
    val not_dialed: Int,
    val status: Int,
    val updated_at: String
) : Serializable