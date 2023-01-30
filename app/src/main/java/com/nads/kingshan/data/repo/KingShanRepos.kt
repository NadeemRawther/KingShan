package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body

interface KingShanRepos {

    suspend fun getPlanets(): Result<List<PlanetModel>>
    suspend fun getVehicle(): Result<List<VehicleModel>>
    suspend fun getToken(): Result<TokenModel>
    suspend fun finders(findRequest: FindRequest): Result<FindResponse>


}