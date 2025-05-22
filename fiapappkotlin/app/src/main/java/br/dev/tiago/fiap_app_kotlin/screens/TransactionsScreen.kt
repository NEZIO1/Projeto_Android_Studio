package br.dev.tiago.fiap_app_kotlin.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.dev.tiago.fiap_app_kotlin.viewmodel.TransactionViewModel
import br.dev.tiago.fiap_app_kotlin.data.local.TransactionEntity
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@Composable
fun TransactionsScreen(navController: NavHostController, viewModel: TransactionViewModel) {
    val transactions by viewModel.transactions.collectAsState()
    val totalBalance by viewModel.totalBalance.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Receita") }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedFilter by remember { mutableStateOf("Todos") }

    val transactionTypes = listOf("Receita", "Despesa", "Investimento")
    val filterOptions = listOf("Todos") + transactionTypes
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    val isFormValid = description.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()

    val filteredTransactions = transactions.filter { transaction ->
        (selectedFilter == "Todos" || transaction.type == selectedFilter) &&
                (searchQuery.text.isBlank() || transaction.description.contains(searchQuery.text, ignoreCase = true)
                        || transaction.category.contains(searchQuery.text, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gestão de Gastos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por descrição ou categoria") },
            modifier = Modifier.fillMaxWidth()
        )

        FilterDropdown(
            label = "Filtrar por Tipo",
            selectedItem = selectedFilter,
            items = filterOptions,
            onItemSelected = { selectedFilter = it },
            dropdownColor = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { if (it.length <= 100) description = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                val newValue = it.replace("[^0-9]".toRegex(), "")
                if (newValue.length <= 11) {
                    amount = if (newValue.isNotEmpty()) {
                        val formatted = newValue.toDouble() / 100
                        currencyFormatter.format(formatted)
                    } else {
                        ""
                    }
                }
            },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = category,
            onValueChange = { if (it.length <= 100) category = it },
            label = { Text("Categoria") },
            modifier = Modifier.fillMaxWidth()
        )

        FilterDropdown(
            label = "Tipo de Transação",
            selectedItem = selectedType,
            items = transactionTypes,
            onItemSelected = { selectedType = it },
            dropdownColor = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (isFormValid) {
                    coroutineScope.launch {
                        viewModel.addTransaction(
                            TransactionEntity(
                                description = description,
                                amount = amount.replace("[^0-9,]".toRegex(), "").replace(",", ".").toDouble(),
                                category = category,
                                type = selectedType,
                                date = System.currentTimeMillis()
                            )
                        )
                        description = ""
                        amount = ""
                        category = ""
                        selectedType = "Receita"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Adicionar Transação")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredTransactions) { transaction ->
                TransactionItem(transaction, viewModel)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionEntity, viewModel: TransactionViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = transaction.description, style = MaterialTheme.typography.bodyLarge)
            Text(text = "R$ ${"%.2f".format(transaction.amount)}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Categoria: ${transaction.category}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Tipo: ${transaction.type}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.removeTransaction(transaction)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Remover")
            }
        }
    }
}

@Composable
fun FilterDropdown(label: String, selectedItem: String, items: List<String>, onItemSelected: (String) -> Unit, dropdownColor: Color) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 4.dp))
        Box {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Abrir menu", tint = dropdownColor)
                    }
                }
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item, color = dropdownColor) }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}
