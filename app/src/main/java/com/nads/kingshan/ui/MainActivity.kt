package com.nads.kingshan.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.nads.kingshan.ui.playground.PlayNavigation
import com.nads.kingshan.ui.theme.KingShanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            KingShanTheme {
                val scrollviewBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

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
                            Greeting(name = "Hello world", modifier =Modifier.padding(innerpadding) )
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