package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ReceiveModel(
    val firstName: String,
    val lastName: String,
)
