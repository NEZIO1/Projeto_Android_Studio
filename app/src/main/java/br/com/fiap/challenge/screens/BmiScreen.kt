package br.com.fiap.challenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BmiScreen( navController: NavController?) {
    Surface (
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .background(Color.Blue)
                .padding(top= 64.dp, start = 32.dp, end = 32.dp)
        ) {
            Text(text = "Oi ...")
            Text(
                text = "Voltar",
                modifier = Modifier.clickable {
                    navController!!.navigate("home")
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BmiScreenPreview() {
    BmiScreen(null)
}