package com.example.easydialer.models

data class Summary(
    val contacted: Int,
    val not_contacted: Int,
    val pending: Int,
    val scheduled: Int
)