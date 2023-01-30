package com.nads.kingshan.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nads.kingshan.ui.kingshanfail.KingShanFail
import com.nads.kingshan.ui.kingshanwin.KingShanWin
import com.nads.kingshan.ui.main.MainViewModel
import com.nads.kingshan.ui.playground.PlayNavigation
import com.nads.kingshan.ui.theme.KingShanTheme
import com.nads.kingshan.ui.vehicle.ProgressBars
import com.nads.kingshan.ui.vehicle.VehicleCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            KingShanTheme {
                val scrollviewBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val error = viewModel.error.collectAsState()
                val less = viewModel.less.collectAsState()
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
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        content = {
                                innerpadding->
                            KingNavHost(navController,viewModel,innerpadding)
                            if (error.value){
                                LaunchedEffect(key1 = snackbarHostState ){
                                    snackbarHostState.showSnackbar("You Can't add more than 4 Items Kindly \n " +
                                            "Change old values to Select to add new values")
                                }
                            }
                            if (less.value){
                                LaunchedEffect(key1 = snackbarHostState ){
                                    snackbarHostState.showSnackbar("You Should add 4 Items")
                                }

                            }

                        },
                        modifier = Modifier.fillMaxSize(),
                        topBar = {

                            TopAppBar(
                                scrollBehavior= scrollviewBehavior,
                                title = {
                                    Text(
                                        "Finding Falcone",
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

                        },
                        bottomBar = {
                          Column(modifier = Modifier
                              .fillMaxWidth()
                              .background(Color.Cyan)) {
                              Text(text = "Bottom Bar", textAlign = TextAlign.Center
                                  , modifier = Modifier.fillMaxWidth())
                          }

                            
                        }
                    )
                }
                )
            }
        }
    }
}


@Composable
fun KingNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    innerpadding:PaddingValues,
) {

    NavHost(navController = navController, startDestination = "kingshanmain"
        , modifier = Modifier.semantics { testTag = "start" },){

        composable("kingshanmain"){
            VehicleScreen(innerpadding,viewModel) { viewModel.find(navController) }
        }
        composable("kingshanwin"){
            KingShanWin(viewModel,innerpadding){
                viewModel.timetaken.value = 0
                navController.navigate("kingshanmain") {
                    popUpTo("kingshanmain")
                }
            }
        }
        composable("kingshanfail"){
            KingShanFail(viewModel,innerpadding){
                viewModel.timetaken.value = 0
                navController.navigate("kingshanmain") {
                    popUpTo("kingshanmain")
                }
            }
        }
    }

}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VehicleScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    onNavigate : ()->Unit,
) {

    val loading by viewModel.loading.collectAsState()
    if (loading) ProgressBars(true) else ProgressBars(false)
    val planetlist = viewModel.planetState.collectAsState()
    val vehiclelist = viewModel.vehicleState.collectAsState()
    val timetaken = viewModel.timetaken.collectAsState()
    val vehicleList by remember{
        mutableStateOf(vehiclelist)
    }

    
    Column(modifier = Modifier.padding(paddingValues)) {







        val listState = rememberLazyListState()
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp), state = listState,
            modifier = Modifier.padding(10.dp)) {
            itemsIndexed(planetlist.value) { index,planetItem ->
                 VehicleCard(
                    planetItem,
                    vehicleList,
                     index,
                     viewModel
                )
            }

        }
        Button(onClick = { onNavigate()},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .defaultMinSize(150.dp)) {
            Column() {
                Text(text = "Submit")
                Text(text="Time Take : ${timetaken.value.toString()}")
            }
        }
    }

}
