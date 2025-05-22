package br.com.fiap.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challenge.screens.BmiScreen
import br.com.fiap.challenge.screens.HomeScreen
import br.com.fiap.challenge.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloWorldTheme {

            val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ){
                    composable(route = "home") { HomeScreen(navController) }
                    composable(route = "bmi") { BmiScreen(navController) }
                }

                //HomeScreen()
                //BmiScreen()
            }
        }
    }
}