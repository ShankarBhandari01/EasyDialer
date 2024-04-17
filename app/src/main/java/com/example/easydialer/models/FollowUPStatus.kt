package com.example.easydialer.models

import java.io.Serializable

data class FollowUPStatus(
    var name: String,
    var campaign_id: Int,
    var type: String,
    var status: Int
) : Serializable