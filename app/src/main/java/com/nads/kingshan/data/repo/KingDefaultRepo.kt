package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.*
import com.nads.kingshan.data.remote.DataSource
import com.nads.kingshan.di.ApplicationScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KingDefaultRepo @Inject constructor(
    private val apiSource:DataSource,
    @ApplicationScope private val externalScope: CoroutineScope):KingShanRepos {

    override suspend fun getPlanets(): Result<List<PlanetModel>> {
       return Result.success(apiSource.getPlanet())
    }

    override suspend fun getVehicle(): Result<List<VehicleModel>> {
       return Result.success(apiSource.getVehicle())
    }

    override suspend fun getToken(): Result<TokenModel> {
        return Result.success(apiSource.getToken())
    }

    override suspend fun find(findRequest:FindRequest): Result<FindResponse> {
        return Result.success(apiSource.find(findRequest))
    }


}