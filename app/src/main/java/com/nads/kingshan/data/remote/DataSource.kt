package com.nads.kingshan.data.remote

import com.nads.kingshan.data.model.FindResponse
import com.nads.kingshan.data.model.PlanetModel
import com.nads.kingshan.data.model.VehicleModel
import okhttp3.RequestBody
import retrofit2.http.*

interface DataSource {
    @GET("planets")
    suspend fun getPlanet():List<PlanetModel>

    @GET("vehicles")
    suspend fun getVehicle():List<VehicleModel>

    @POST("token")
    suspend fun getToken():String

    @POST("find")
    suspend fun find(@Body findRequest:RequestBody):FindResponse


}