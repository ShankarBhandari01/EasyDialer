package com.example.easydialer.models

import java.io.Serializable

data class AgentItem(
    val address: String,
    val created_at: String,
    val description: Any,
    val id: Int,
    val name: String,
    val password: String,
    val phone: String,
    val role: String,
    val status: Int,
    val updated_at: String,
    val username: String
) : Serializable