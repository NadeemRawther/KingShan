package com.nads.kingshan.data.model

import com.google.gson.annotations.SerializedName

data class FindResponse(@SerializedName("planet_name")
                        val planetName:String?,
                        val status:String?,
                        val error:Boolean?)
