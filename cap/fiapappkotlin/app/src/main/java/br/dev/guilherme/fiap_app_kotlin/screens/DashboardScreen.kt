package br.dev.guilherme.fiap_app_kotlin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.dev.guilherme.fiap_app_kotlin.ui.theme.GreenPrimary
import br.dev.guilherme.fiap_app_kotlin.ui.theme.YellowPrimary
import br.dev.guilherme.fiap_app_kotlin.viewmodel.DashboardViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel) {
    val totalInvestments by viewModel.totalInvestments.collectAsState(initial = 0.0)
    val totalRevenue by viewModel.totalRevenue.collectAsState(initial = 0.0)
    val totalExpenses by viewModel.totalExpenses.collectAsState(initial = 0.0)
    val goalAmount by viewModel.goalAmount.collectAsState(initial = 0.0)

    val currentBalance = (totalRevenue ?: 0.0) - (totalExpenses ?: 0.0)
    var goalInput by remember { mutableStateOf("") }
    var showGoalReachedDialog by remember { mutableStateOf(false) }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    LaunchedEffect(currentBalance, goalAmount) {
        if (goalAmount > 0 && currentBalance >= goalAmount) {
            showGoalReachedDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resumo Financeiro",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var selectedMonth by remember { mutableStateOf(viewModel.selectedMonth.value) }
            var selectedYear by remember { mutableStateOf(viewModel.selectedYear.value) }

            val months = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
            val years = listOf("2023", "2024", "2025")

            DropdownSelector("Mês: $selectedMonth", months) {
                selectedMonth = it
                viewModel.setSelectedMonth(it)
            }

            DropdownSelector("Ano: $selectedYear", years) {
                selectedYear = it
                viewModel.setSelectedYear(it)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(YellowPrimary, shape = RoundedCornerShape(12.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Investimentos: ${currencyFormatter.format(totalInvestments ?: 0.0)}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Receitas: ${currencyFormatter.format(totalRevenue ?: 0.0)}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Despesas: ${currencyFormatter.format(totalExpenses ?: 0.0)}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Saldo Atual: ${currencyFormatter.format(currentBalance)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = GreenPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (goalAmount > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Meta Atual: ${currencyFormatter.format(goalAmount)}", style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = { viewModel.setGoalAmount(0.0) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Remover Meta")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = goalInput,
            onValueChange = { goalInput = it },
            label = { Text("Definir Meta (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val goalValue = goalInput.toDoubleOrNull()
                if (goalValue != null && goalValue > 0) {
                    viewModel.setGoalAmount(goalValue)
                    goalInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("Salvar Meta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showGoalReachedDialog) {
            AlertDialog(
                onDismissRequest = { showGoalReachedDialog = false },
                confirmButton = {
                    Button(
                        onClick = { showGoalReachedDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                    ) {
                        Text("Fechar")
                    }
                },
                title = { Text("Meta Atingida!", color = GreenPrimary) },
                text = { Text("Parabéns! Você atingiu sua meta de ${currencyFormatter.format(goalAmount ?: 0.0)}.") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            NavigationButton(navController, "transactions", "Gerenciar Gastos")
            NavigationButton(navController, "news", "Notícias")
            NavigationButton(navController, "about", "Sobre Nós")
        }
    }
}

@Composable
fun NavigationButton(navController: NavController, route: String, text: String) {
    Button(
        onClick = { navController.navigate(route) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
    ) {
        Text(text)
    }
}

@Composable
fun DropdownSelector(label: String, options: List<String>, onSelection: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(label)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onSelection(option)
                    expanded = false
                })
            }
        }
    }
}
