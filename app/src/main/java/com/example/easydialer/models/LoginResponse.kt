package com.example.easydialer.models

data class LoginResponse(
    val message: String,
    val status: Boolean,
    val data: Agent,
    val menus: ArrayList<Menus>
)