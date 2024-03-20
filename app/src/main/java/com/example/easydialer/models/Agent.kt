package com.example.easydialer.models

import com.google.gson.annotations.SerializedName

data class Agent(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)



