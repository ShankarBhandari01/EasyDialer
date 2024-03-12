package com.example.easydialer.models

import java.io.Serializable

data class Login(
    val email: String,
    val password: String,
    val role: String
):Serializable