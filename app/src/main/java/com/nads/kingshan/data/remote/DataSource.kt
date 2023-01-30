package com.nads.kingshan.data.remote

import com.nads.kingshan.data.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface DataSource {
    @GET("planets")
    suspend fun getPlanet():List<PlanetModel>

    @GET("vehicles")
    suspend fun getVehicle():List<VehicleModel>


    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("token")
    suspend fun getToken():TokenModel

    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("find")
    suspend fun find(@Body findRequest:FindRequest):FindResponse


}