package com.nads.kingshan.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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



    //list for compose spinners to access and create find falcone request
    private val _planetlist = MutableStateFlow(mutableListOf<String>())
    val planetlistState: StateFlow<List<String>> get() = _planetlist
    private val _vehiclelist = MutableStateFlow(mutableListOf<MyData>())
    val vehiclelistState: StateFlow<List<MyData>> get() = _vehiclelist

    //for findrequest
    private val _vehiclelistString = MutableStateFlow(mutableListOf<String>())
    val vehiclelistStateString: StateFlow<List<String>> get() = _vehiclelistString

//
//   //request models
    private val _requestModel = MutableStateFlow(FindRequest(token.value.toString(),
        planetlistState.value
        ,vehiclelistStateString.value ))
   val requestModel: StateFlow<FindRequest> get() = _requestModel

    private val _response = MutableStateFlow(FindResponse("","",""))
    val response: StateFlow<FindResponse> get() = _response



    private val _online = MutableStateFlow(true)
    val online: MutableStateFlow<Boolean> get() = _online
    private val _error = MutableStateFlow(false)
    val error: MutableStateFlow<Boolean> get() = _error




    fun getVehicles(){

        viewModelScope.launch {
           val vehicleData =  kingShanRepos.getVehicle()
            if (vehicleData.isSuccess){
                _vehiclelist.value.add(MyData(Int.MAX_VALUE,"Select",true,1))
                vehicleData.map {
                    _vehicles.emit(it)

                    it.map() {
                        for (i in 1 ..it.total_no){
                            _vehiclelist.value.add(MyData(it.max_distance,it.name,true,i))
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

        }
    }
    fun find(){
        viewModelScope.launch {
               kingShanRepos.find(findRequest = requestModel.value)
        }
    }

    fun changeEnable(enable: Boolean, index: Int, oldIndex: Int,planetModel: PlanetModel){
        viewModelScope.launch {
            if (_vehiclelist.value.get(index).name != "Select"){
                _vehiclelist.value.get(index).enable = enable
                _planetlist.value.add(planetModel.name)
            }
            _vehiclelist.value.get(oldIndex).enable = true

        }
    }






}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val userData: VehicleModel) : MainActivityUiState
}


