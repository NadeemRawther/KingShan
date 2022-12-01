package com.nads.kingshan.ui.playground


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nads.kingshan.R
import com.nads.kingshan.ui.theme.Pink80
import com.nads.kingshan.ui.theme.PurpleGrey80

@Composable
fun PlayNavigation(navController:NavController) {

    Column(modifier = Modifier.fillMaxWidth()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Pink80)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Pink80)) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .height(15.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.kingshan),
                contentDescription = "Nothing",
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxHeight()
                    .padding(20.dp, 12.dp, 10.dp)
            )


//        items.forEach { item ->
//
//            /* Add code later */
//
//        }
            Text(
                text = "Developed \n by KingShan",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding( top = 25.dp)


            )
        }


        OutlinedButton(onClick = { /*..*/ }, modifier = Modifier
            .widthIn(min = 200.dp)
            .padding(top = 10.dp, start = 25.dp).height(40.dp), colors = ButtonDefaults.buttonColors()) {
            Icon(painterResource(id = R.drawable.home), contentDescription = "Localized description")
            Text(text = stringResource(id = R.string.home), maxLines = 1)
        }

        OutlinedButton(onClick = { /*..*/ },modifier = Modifier

            .widthIn(min = 200.dp)
            .padding(top = 10.dp, start = 25.dp).height(40.dp)
        ,colors = ButtonDefaults.buttonColors()) {
            Icon( painterResource(id = R.drawable.settingsicon), contentDescription = "Localized description")
            Text(text = stringResource(id = R.string.settings), maxLines = 1)
        }

        OutlinedButton(onClick = { /*..*/ },modifier = Modifier
            .widthIn(min = 200.dp)
            .padding(top = 10.dp, start = 25.dp).height(40.dp),
            colors = ButtonDefaults.buttonColors()) {
            Icon(painterResource(id = R.drawable.logout), contentDescription = "Localized description")
            Text(text = stringResource(id = R.string.logout), maxLines = 1)
        }
    }
}