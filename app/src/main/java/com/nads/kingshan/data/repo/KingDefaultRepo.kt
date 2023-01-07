package com.nads.kingshan.data.repo

import com.nads.kingshan.data.model.FindResponse
import com.nads.kingshan.data.model.PlanetModel
import com.nads.kingshan.data.model.VehicleModel
import com.nads.kingshan.data.remote.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KingDefaultRepo @Inject constructor(
private val apiSource:DataSource,
private val dispatcher: CoroutineDispatcher = Dispatchers.IO):KingShanRepos {
    override fun getPlanets(): List<PlanetModel> {
        TODO("Not yet implemented")
    }

    override fun getVehicle(): List<VehicleModel> {
        TODO("Not yet implemented")
    }

    override fun getToken(): String {
        TODO("Not yet implemented")
    }

    override fun find(findRequest: RequestBody): FindResponse {
        TODO("Not yet implemented")
    }


}