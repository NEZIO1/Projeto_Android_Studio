package br.dev.tiago.fiap_app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import br.dev.tiago.fiap_app_kotlin.ui.theme.FinanceAppTheme
import br.dev.tiago.fiap_app_kotlin.screens.*
import br.dev.tiago.fiap_app_kotlin.data.local.AppDatabase
import br.dev.tiago.fiap_app_kotlin.repository.TransactionRepository
import br.dev.tiago.fiap_app_kotlin.repository.UserRepository
import br.dev.tiago.fiap_app_kotlin.viewmodel.TransactionViewModel
import br.dev.tiago.fiap_app_kotlin.viewmodel.UserViewModel
import br.dev.tiago.fiap_app_kotlin.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val userRepository by lazy { UserRepository(database.userDao()) }

    private val transactionViewModel: TransactionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TransactionViewModel(transactionRepository) as T
            }
        }
    }

    private val dashboardViewModel: DashboardViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel(transactionRepository) as T
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceAppTheme {
                AppNavigation(transactionViewModel, userViewModel, dashboardViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(
    transactionViewModel: TransactionViewModel,
    userViewModel: UserViewModel,
    dashboardViewModel: DashboardViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") { OnboardingScreen(navController, userViewModel) }
        composable("dashboard") { DashboardScreen(navController, dashboardViewModel) }
        composable("transactions") { TransactionsScreen(navController, transactionViewModel) }
        composable("news") { NewsScreen(navController) }
        composable("about") { AboutScreen() }
    }
}
