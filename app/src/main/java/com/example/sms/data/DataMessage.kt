package com.example.sms.data

data class DataMessage(
    val address: String,
    val messages: List<String>
)

data class Message(
    val address: String,
    val message: String
)
