package com.nads.kingshan.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nads.kingshan.data.model.FindRequest
import com.nads.kingshan.ui.main.MainViewModel
import com.nads.kingshan.ui.playground.PlayNavigation
import com.nads.kingshan.ui.theme.KingShanTheme
import com.nads.kingshan.ui.vehicle.ProgressBars
import com.nads.kingshan.ui.vehicle.VehicleCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            KingShanTheme {
                val scrollviewBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                viewModel.getVehicles()
                viewModel.getPlanets()
                viewModel.getToken()



                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier= Modifier.width(300.dp),
                            drawerShape = MaterialTheme.shapes.large,
                        drawerTonalElevation = 4.dp,
                        drawerContainerColor =MaterialTheme.colorScheme.primaryContainer,
                        drawerContentColor = MaterialTheme.colorScheme.background,
                        content = {

                           PlayNavigation(navController)
                        }
                        )
                    },
                    gesturesEnabled = true,
                scrimColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                content = {
                    Scaffold(
                        content = {
                                innerpadding->
//                            Greeting(name = "Hello world ${planetlist.value.toString()} " + "\n" +
//                                    "${vehiclelist.value.toString()}  " + "\n" +
//                                    "${token.value}",
//                                modifier =Modifier.padding(innerpadding) )
                            VehicleScreen(innerpadding,viewModel,navController)

                        },
                        modifier = Modifier.fillMaxSize(),
                        topBar = {

                            TopAppBar(
                                scrollBehavior= scrollviewBehavior,
                                title = {
                                    Text(
                                        "King Shan",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {  drawerState.open()
                                        } }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                }
                            )

                        }
                    )
                }
                )

            }
        }
    }
}

@Composable
fun VehicleScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel ,
    navController: NavHostController
) {

    val loading by viewModel.loading.collectAsState()
    if (loading) ProgressBars(true) else ProgressBars(false)
    val planetlist = viewModel.planetState.collectAsState()
    val vehiclelist = viewModel.vehicleState.collectAsState()
    val token = viewModel.token.collectAsState()
    var vehicleList by remember{
        mutableStateOf(vehiclelist)
    }



    var submit by remember { mutableStateOf(false) }
    var findRequest by remember {
        mutableStateOf(FindRequest("", emptyList(), emptyList()))
    }
    val myData = viewModel.vehiclelistState.collectAsState()
    val planetData = viewModel.planetlistState.collectAsState()
//    by remember {
//        mutableStateOf(mutableListOf(MyData(0,0,"Select")))
//    }

    Column(modifier = Modifier.padding(paddingValues)) {


        val listState = rememberLazyListState()
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp), state = listState,
            modifier = Modifier.padding(10.dp)) {





            itemsIndexed(planetlist.value) { index,planetItem ->






                 VehicleCard(
                    navController,
                    planetItem,
                    vehicleList,
                     index,
                     viewModel

                )
            }

        }
        Button(onClick = { submit = true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Submit")
        }

    }

}

@Composable
fun Greeting(name: String,modifier: Modifier) {
    Text(text = "Hello $name!", modifier = modifier
        .background(Color.Green)
        .fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KingShanTheme {
        Greeting("Android", modifier = Modifier)
    }
}