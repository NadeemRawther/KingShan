package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.FindResponse
import com.nads.kingshan.data.model.PlanetModel
import com.nads.kingshan.data.model.VehicleModel
import okhttp3.RequestBody
import retrofit2.http.Body

interface KingShanRepos {

    fun getPlanets():List<PlanetModel>
    fun getVehicle():List<VehicleModel>
    fun getToken():String
    fun find(findRequest: RequestBody): FindResponse


}