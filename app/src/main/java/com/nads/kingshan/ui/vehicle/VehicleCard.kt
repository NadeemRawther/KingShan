package com.nads.kingshan.ui.vehicle


import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nads.kingshan.R
import com.nads.kingshan.data.model.PlanetModel
import com.nads.kingshan.data.model.VehicleModel
import com.nads.kingshan.ui.main.MainViewModel

@Composable
fun VehicleCard(

      planetlist: PlanetModel,
      vehiclelist: State<List<VehicleModel>>,
      index: Int,
      viewModel: MainViewModel,
    //  myData:List<MyData>
) {
      val painter: Painter = when(planetlist.name){
            "Donlon" -> painterResource(id = R.drawable.donlon)
            "Enchai" -> painterResource(id = R.drawable.enchai)
            "Jebing" -> painterResource(id = R.drawable.jebing)
            "Sapir" -> painterResource(id = R.drawable.sapir)
            "Lerbin" -> painterResource(id = R.drawable.lerbin)
            "Pingasor" -> painterResource(id = R.drawable.pingasor)
            else -> painterResource(id = R.drawable.home)
      }


      val myData = viewModel.vehiclelistState.collectAsState()


      Card(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp)
            .clip(
                  shape = RoundedCornerShape(
                        10.dp
                  )
            )
            .clickable {

            }
            .background(Color(0xfffdedec))
            .padding(10.dp)
            .semantics {

            }) {
            Row() {
                  Image(
                       painter = painter,
                        contentDescription = "just a profile avatar image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                              .clip(CircleShape)
                              .size(50.dp),

                  )
                  Column(modifier = Modifier.padding(start = 10.dp)) {

                        Text(
                              text = "planet: " +
                                      planetlist.name +
                                      ", distance: " +
                                      planetlist.distance ,
                              Modifier.fillMaxWidth()
                        )
                        SpinnerSample(
                              myData.value,
                             // preselected = myData.get(0),

                              modifier = Modifier.fillMaxWidth(),
                              viewModel,
                              planetlist
                        )
                  }


            }
      }
}
@Composable
fun ProgressBars(enable:Boolean) {
      val progressValue = 0.99f
      val infiniteTransition = rememberInfiniteTransition()

      val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue,
            animationSpec = infiniteRepeatable(animation = tween(900))
      )


      if (enable) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                  CircularProgressIndicator(progress = progressAnimationValue)

            }
      }
}

@Preview
@Composable
fun progressprev() {
      ProgressBars(enable = true)

}


@Composable
fun SpinnerSample(
      list: List<MyData>,
      modifier: Modifier = Modifier,
      viewModel: MainViewModel,
      planetlist: PlanetModel
) {


   var selected by remember { mutableStateOf(list[0]) }
      var expanded by remember { mutableStateOf(false) }

      OutlinedCard(
            modifier = modifier.clickable {
                  expanded = !expanded
            }
      ) {


            Row(
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.Top,
            ) {

                  Text(
                        text = selected.name,
                        modifier = Modifier
                              .weight(1f)
                              .padding(horizontal = 16.dp, vertical = 8.dp)
                  )
                  Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))

                  DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.wrapContentWidth()   // delete this modifier and use .wrapContentWidth() if you would like to wrap the dropdown menu around the content
                  ) {
                        list.forEach { listEntry ->
                            if (listEntry.distance >= planetlist.distance ) {
                              DropdownMenuItem(
                                    enabled = listEntry.enable,
                                    onClick = {
                                          viewModel.changeEnable(enable = false,index= list.indexOf(listEntry),
                                                oldIndex = list.indexOf(selected), planetModel = planetlist )
                                          if (!viewModel.error.value){
                                                selected = listEntry
                                          }

                                          expanded = false

                                    },
                                    text = {
                                          Text(
                                                text = listEntry.name,
                                                modifier = Modifier
                                                      .wrapContentWidth()
                                                      .fillMaxWidth()
                                                      .align(Alignment.Start),
                                          )
                                    },
                              )
                          }
                        }
                  }
            }
      }
}


@Preview(showBackground = true)
@Composable
fun SpinnerSample_Preview() {
      MaterialTheme {

      }
}

data class MyData (
      val distance: Int,
      val name: String,
      var enable:Boolean=false,
      var id:Int,
      var speed:Int
)
