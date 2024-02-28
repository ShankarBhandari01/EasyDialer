package com.example.easydialer.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class ApiResponseData(
    var Message: String? = null,
    var ErrorCode: String? = null,
    var Data: DataModel? = null
)

data class DataModel(
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

data class Product(
    var imgUrl: String? = null,
    var productId: Int? = null,
    var localPrice: Double? = null,
    var name: String? = null,
    var rank: Float? = null,
)


