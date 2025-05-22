package br.dev.tiago.fiap_app_kotlin.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "📌 Sobre Nós",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🎯 Objetivo do Aplicativo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "O aplicativo tem como principal objetivo facilitar a gestão financeira para pessoas de baixa renda, proporcionando uma visão clara e centralizada de suas despesas, receitas e investimentos. Além disso, oferece a possibilidade de definição de metas financeiras para estimular um saldo positivo e integra notícias financeiras atualizadas, permitindo aos usuários uma gestão financeira mais informada e consciente.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "👨‍💻 Equipe de Desenvolvimento",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        TeamMember("Tiago Lelis Stehling Santos - RM 557771", "https://www.linkedin.com/in/tiago-lelis-240286161/", context)
        TeamMember("Leandro Solany Pinheiro Medeiros - RM 556505", "https://www.linkedin.com/in/leandro-solany-6b053712a/", context)
        TeamMember("Guilherme Augusto Nézio de Oliveira - RM 556682", "https://www.linkedin.com/in/guilhermenezio/", context)
        TeamMember("João Gayjutz Simões - RM 555856", "https://www.linkedin.com/in/gayjutz/", context)
    }
}

@Composable
fun TeamMember(name: String, linkedinUrl: String, context: android.content.Context) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(
            text = "🔗 LinkedIn",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                context.startActivity(intent)
            }
        )
    }
}
