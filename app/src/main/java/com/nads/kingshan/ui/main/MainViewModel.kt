package com.nads.kingshan.ui.main


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.nads.kingshan.data.model.*
import com.nads.kingshan.data.repo.KingShanRepos
import com.nads.kingshan.ui.vehicle.MyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val kingShanRepos: KingShanRepos
,private val savedStateHandle: SavedStateHandle):ViewModel () {

    private val _vehicles = MutableStateFlow(listOf<VehicleModel>())
    val vehicleState: StateFlow<List<VehicleModel>> get() = _vehicles
    private val _planetModel = MutableStateFlow(listOf<PlanetModel>())
    val planetState: StateFlow<List<PlanetModel>> get() = _planetModel
    private val _token = MutableStateFlow(TokenModel(""))
    val token: MutableStateFlow<TokenModel> get() = _token
    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading
    private val _timetaken = MutableStateFlow(0)
    val timetaken: MutableStateFlow<Int> get() = _timetaken
    //list for compose spinners to access and create find falcone request
    private val _planetlist = MutableStateFlow(mutableListOf<PlanetLis>())
    val planetlistState: StateFlow<List<PlanetLis>> get() = _planetlist
    private val _vehiclelist = MutableStateFlow(mutableListOf<MyData>())
    val vehiclelistState: StateFlow<List<MyData>> get() = _vehiclelist

    //for findrequest
    private val vehicleListString = mutableListOf<String>()
    private val planetListString = mutableListOf<String>()




    private val _response = MutableStateFlow(FindResponse("","",false))
    val response: StateFlow<FindResponse> get() = _response



    private val _online = MutableStateFlow(true)
    val online: MutableStateFlow<Boolean> get() = _online
    private val _error = MutableStateFlow(false)
    val error: MutableStateFlow<Boolean> get() = _error
    private val _less = MutableStateFlow(false)
    val less: MutableStateFlow<Boolean> get() = _less




    fun getVehicles(){
       _loading.value = true
        viewModelScope.launch {
           val vehicleData =  kingShanRepos.getVehicle()
            if (vehicleData.isSuccess){
                _vehiclelist.value.add(MyData(Int.MAX_VALUE,"Select",true,1,0))
                vehicleData.map {
                    _vehicles.emit(it)
                    it.map() {
                        for (i in 1 ..it.total_no){
                            _vehiclelist.value.add(MyData(
                                it.max_distance
                                ,it.name
                                ,true,i,it.speed))
                        }

                    }
                }
            }

        }
    }

    fun getPlanets(){
      viewModelScope.launch {
          val planetData = kingShanRepos.getPlanets()
          planetData.map {
              _planetModel.emit(it)
              it.map {

//                  _planetlist.value.add(it.name)
              }
          }
      }
    }

    fun getToken(){
        viewModelScope.launch {
            val token = kingShanRepos.getToken()
            token.map {
                _token.emit(it)
            }
        _loading.value = false
        }
    }

    fun find(navController: NavHostController) {

        if (planetlistState.value.size <= 3){
            _less.value = true
        }else {
           _loading.value = true
            viewModelScope.launch {
                _less.value =false
                for (request in planetlistState.value) {
                    vehicleListString.add(request.vehicleName)
                    planetListString.add(request.planetName)
                }

                val gson = Gson()
                val vehicleListJson = gson.toJson(vehicleListString)
                val planetListJson = gson.toJson(planetListString)
                val vehicleListTypeAdapter = VehicleListTypeAdapter()
                val planetListTypeAdapter = PlanetListTypeAdapter()
                val vehicleList = vehicleListTypeAdapter
                    .fromJson(gson.fromJson(vehicleListJson, JsonArray::class.java))
                val planetList = planetListTypeAdapter
                    .fromJson(gson.fromJson(planetListJson, JsonArray::class.java))
                val data = kingShanRepos
                    .finders(
                        FindRequest(
                            token = token.value.token,
                            vehicleNames = vehicleList, planetNames = planetList
                        )
                    )
                if (data.isSuccess) {
                    data.map {
                        _response.emit(it)
                    }
                }
                if (_response.value.status == "success"){
                    navController.navigate("kingshanwin")
                   clearList()
                }else{
                    navController.navigate("kingshanfail")
                   clearList()
                }

                clearList()
                _loading.value = false
            }

        }

        }

    private fun clearList(){
        for (i in 0 until _vehiclelist.value.size){
            _vehiclelist.value[i].enable = true
        }
        vehicleListString.clear()
        _planetlist.value.clear()
        planetListString.clear()
    }

    fun changeEnable(enable: Boolean, index: Int,
                     oldIndex: Int,planetModel: PlanetModel){
        viewModelScope.launch {
            //planetlist should not be over 4
            // select shouldnt be added to planetlist
            //planet name and vehicle name should be added to planetlist
            //if over 4 then we need to change any choosen planet to select select
            //also we can change already selected planet vehicle list
            /*
            IF PLANETLIST LESS THAN 4 OR vehiclelist oldindex is not equal to "Select":
                            if vehiclelist newindex is "Select" AND vehiclelist oldindex ISNOT "select":
                                    planetlist remove vehiclelist.oldindex
                                    vehiclelist.value oldindex enable = true
                           else if vehiclelist newindex is not "Select" AND vehiclelist oldindex IS NOT SELECT :
                                planetlist FIND OLDINDEX AND REMOVE
                                ADD VALUE TO PLANETLIST
                                vehiclelist value index enable = false
                                vehiclelist value index enable  = true

                           ELSE IF  vehiclelist newindex is not "Select" :
                                planetlist ADD newindex value
                                vehiclelist value index enable  = false
             */

            if(_planetlist.value.size < 4 || _vehiclelist.value[oldIndex].name != "Select"){
                _error.value = false
                if (_vehiclelist.value[index].name == "Select" && _vehiclelist.value[oldIndex].name != "Select"){
                           val id = _planetlist.value.indexOf(PlanetLis(planetModel.name,_vehiclelist.value[oldIndex].name,planetModel.distance))

                            val speed = _planetlist.value[id].distance/_vehiclelist.value[oldIndex].speed
                            _timetaken.value -= speed
                            _planetlist.value.removeAt(id)
                            _vehiclelist.value[oldIndex].enable = true

                }
                else if (_vehiclelist.value[index].name != "Select" && _vehiclelist.value[oldIndex].name != "Select"){
                    val id = _planetlist.value.indexOf(PlanetLis(planetModel.name,_vehiclelist.value[oldIndex].name,planetModel.distance))
                    val speed = _planetlist.value[id].distance/_vehiclelist.value[oldIndex].speed
                    _timetaken.value -= speed
                    _planetlist.value.removeAt(id)
                    _planetlist.value.add(PlanetLis(planetModel.name,_vehiclelist.value[index].name,planetModel.distance))
                    _vehiclelist.value[oldIndex].enable = true
                    _vehiclelist.value[index].enable = enable
                    val currspeed = planetModel.distance/_vehiclelist.value[index].speed
                    _timetaken.value += currspeed
                }
                else if(_vehiclelist.value[index].name != "Select"){
                    _planetlist.value.add(PlanetLis(planetModel.name,_vehiclelist.value[index].name,planetModel.distance))
                    _vehiclelist.value[index].enable = enable
                    val currspeed = planetModel.distance/_vehiclelist.value[index].speed
                    _timetaken.value += currspeed

                }

            }else{
                _error.value = true
            }

        }
    }






}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val userData: VehicleModel) : MainActivityUiState
}
data class PlanetLis(val planetName:String, val vehicleName:String,val distance:Int)


