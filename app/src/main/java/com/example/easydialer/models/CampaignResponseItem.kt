package com.example.easydialer.models

import java.io.Serializable

data class CampaignResponseItem(
    val created_at: String,
    val description: String,
    val id: Int,
    val key: String,
    val mode: String,
    val name: String,
    val status: Int,
    val updated_at: String
) : Serializable