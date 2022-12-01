package com.nads.kingshan.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nads.kingshan.R

@Composable
fun NormalButton (text:String,@DrawableRes image:Int){
    OutlinedButton(onClick = { /*..*/ }, modifier = Modifier
        .widthIn(min = 200.dp)
        .padding(top = 10.dp, start = 25.dp)
        .height(40.dp), colors = ButtonDefaults.buttonColors()) {
        Icon(painterResource(id = image), contentDescription = "Localized description")
        Text(text = text, maxLines = 1)
    }
}

@Preview
@Composable
fun PreviewNormalButton() {
    NormalButton(text = "JUST",R.drawable.home)
}
