package br.dev.guilherme.fiap_app_kotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.dev.tiago.fiap_app_kotlin.R
import br.dev.guilherme.fiap_app_kotlin.ui.theme.YellowPrimary
import br.dev.guilherme.fiap_app_kotlin.viewmodel.UserViewModel
import java.util.regex.Pattern


@Composable
fun OnboardingScreen(navController: NavController, userViewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoginMode by remember { mutableStateOf(true) }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo do App",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Softtek",
            fontSize = 24.sp,
            color = YellowPrimary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isLoginMode) "Login" else "Cadastro",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            icon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email
        )

        CustomOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = "Senha",
            icon = Icons.Filled.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            passwordVisibility = passwordVisibility,
            onPasswordToggle = { passwordVisibility = !passwordVisibility }
        )

        if (!isLoginMode) {
            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirme a Senha",
                icon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                passwordVisibility = passwordVisibility,
                onPasswordToggle = { passwordVisibility = !passwordVisibility }
            )
        }

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    !isValidEmail(email) -> errorMessage = "Formato de e-mail inválido!"
                    password.length < 6 -> errorMessage = "A senha deve ter pelo menos 6 caracteres!"
                    !isLoginMode && password != confirmPassword -> errorMessage = "As senhas não coincidem!"
                    !isLoginMode -> {
                        userViewModel.registerUser(
                            email.trim(), password.trim(),
                            onSuccess = { navController.navigate("dashboard") },
                            onError = { message -> errorMessage = message }
                        )
                    }
                    else -> {
                        userViewModel.loginUser(
                            email.trim(), password.trim(),
                            onSuccess = { navController.navigate("dashboard") },
                            onError = { message -> errorMessage = message }
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(if (isLoginMode) "Entrar" else "Cadastrar")
        }

        TextButton(
            onClick = { isLoginMode = !isLoginMode },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = if (isLoginMode) "Criar uma conta" else "Já tenho uma conta",
                color = YellowPrimary
            )
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    passwordVisibility: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
    )
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    )
    return emailPattern.matcher(email).matches()
}
