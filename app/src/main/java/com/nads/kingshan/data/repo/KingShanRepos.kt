package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.*
import okhttp3.RequestBody
import retrofit2.http.Body

interface KingShanRepos {

    suspend fun getPlanets(): Result<List<PlanetModel>>
    suspend fun getVehicle(): Result<List<VehicleModel>>
    suspend fun getToken(): Result<TokenModel>
    suspend fun find(findRequest: FindRequest): Result<FindResponse>


}