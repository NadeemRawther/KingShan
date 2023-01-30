package com.nads.kingshan

import com.nads.kingshan.data.model.*
import com.nads.kingshan.data.repo.KingShanRepos

class KingShanFakeRepo:KingShanRepos {
    override suspend fun getPlanets(): Result<List<PlanetModel>> {
        return Result.success(listOf(PlanetModel("Donlon",100),PlanetModel("Enchai",200)
           ,PlanetModel("Jebing",300),PlanetModel("Sapir",400)
           ,PlanetModel("Lerbin",500),PlanetModel("Pingasor",600)))
    }

    override suspend fun getVehicle(): Result<List<VehicleModel>> {
        return Result.success(listOf(VehicleModel("Space pod",2,200,2),
            VehicleModel("Space rocket",1,300,4),
            VehicleModel("Space shuttle",1,400,5),
            VehicleModel("Space ship",2,600,10),))
    }

    override suspend fun getToken(): Result<TokenModel> {
        return Result.success(TokenModel("itWrViaWfictXDGiEGQYsMGAjoxlYCCH"))
    }

    override suspend fun finders(findRequest: FindRequest): Result<FindResponse>{
        if (findRequest.planetNames[0] == "Donlon") {
            return Result.success(FindResponse("Donlon", "success", null))
        }
        return Result.success(FindResponse(null,null,false))
        }


}