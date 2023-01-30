package com.nads.kingshan.ui.kingshanwin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nads.kingshan.ui.main.MainViewModel



@Composable
fun KingShanWin(
    viewModel: MainViewModel,
    inner_padding: PaddingValues,
   onNavigate:()->Unit
) {
        val timeTaken = viewModel.timetaken.collectAsState()
        val planetFound = viewModel.response.collectAsState()

    Column(modifier = Modifier.padding(inner_padding).fillMaxWidth().fillMaxHeight(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
           val modifier = Modifier.fillMaxWidth()
           Text(text = "Finding Falcone", modifier = modifier, textAlign = TextAlign.Center)
           Text(text = "Success! Congratulation on finding falcone \n" +
                   "King shan is mighty pleased ",modifier = modifier, textAlign = TextAlign.Center)
           Text(text = "TimeTaken : ${timeTaken.value}",modifier = modifier, textAlign = TextAlign.Center)
           Text(text = "Planet Found : ${planetFound.value.planetName}",modifier = modifier, textAlign = TextAlign.Center)

           Button(onClick = {onNavigate()},modifier = Modifier
               .width(150.dp)){
               Text(text = "Start Again")
           }




       }
}

