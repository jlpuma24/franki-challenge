package com.frankichallenge.data.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp") val temperature: Double,
)
