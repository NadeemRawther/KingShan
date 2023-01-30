package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.*
import com.nads.kingshan.data.remote.DataSource
import com.nads.kingshan.di.ApplicationScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KingDefaultRepo @Inject constructor(
    private val apiSource:DataSource,
    @ApplicationScope private val externalScope: CoroutineScope):KingShanRepos {

    override suspend fun getPlanets(): Result<List<PlanetModel>> {
       return try {
           Result.success(apiSource.getPlanet())
       }catch (e:HttpException){
           Result.failure(e)
       }
    }

    override suspend fun getVehicle(): Result<List<VehicleModel>> {
       return try {
           Result.success(apiSource.getVehicle())
       }catch (e:HttpException){
           Result.failure(e)
       }
    }

    override suspend fun getToken(): Result<TokenModel> {
        return try {
            Result.success(apiSource.getToken())
        }catch (e:HttpException){
            Result.failure(e)
        }
    }

    override suspend fun finders(findRequest:FindRequest): Result<FindResponse> {
        return try {
            Result.success(apiSource.find(findRequest))
        }catch (e:HttpException){
            Result.failure(e)
        }
    }


}