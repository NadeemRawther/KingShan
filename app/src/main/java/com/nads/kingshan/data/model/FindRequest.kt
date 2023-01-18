package com.nads.kingshan.data.model

import com.google.gson.annotations.SerializedName

data class FindRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("planet_names")
    val planet_names:List<String>,
    @SerializedName("vehicle_names")
    val vehicle_names:List<String>)
