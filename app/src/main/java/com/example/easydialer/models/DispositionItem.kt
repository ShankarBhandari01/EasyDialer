package com.example.easydialer.models

import java.io.Serializable

data class DispositionItem(
    val campaign_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val status: Int,
    val type: String,
    val updated_at: String
): Serializable