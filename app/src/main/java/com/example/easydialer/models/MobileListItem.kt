package com.example.easydialer.models

import java.io.Serializable
import java.time.Duration

data class MobileListItem(
    var campaign_id: String,
    var contacted: Int,
    var contacted_at: Any,
    var created_at: String,
    var description: String,
    var dialed: Int,
    var dialed_at: Any,
    var filename: Any,
    var id: Int,
    var mobile: String,
    var not_contacted: Int,
    var not_dialed: Int,
    var status: Int,
    var updated_at: String,
    var duration: String
) : Serializable