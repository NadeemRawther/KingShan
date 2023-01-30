package com.nads.kingshan.ui.kingshanfail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nads.kingshan.ui.main.MainViewModel


@Composable
fun KingShanFail(
    viewModel: MainViewModel,
    inner_padding: PaddingValues,
    onNavigate:()->Unit
) {

    Column(modifier = Modifier.padding(inner_padding).fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val timeTaken = viewModel.timetaken.collectAsState()
        val planetFound = viewModel.response.collectAsState()
        Text(text = "Finding Falcone")
        Text(text = "Failed! King shan is not pleased ")

        Button(onClick = {onNavigate()}){
            Text(text = "Try Again")
        }




    }


}