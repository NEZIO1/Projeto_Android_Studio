package br.dev.tiago.fiap_app_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.tiago.fiap_app_kotlin.data.local.UserEntity
import br.dev.tiago.fiap_app_kotlin.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val existingUser = repository.getUser(email, password)
            if (existingUser != null) {
                onError("Usuário já cadastrado!")
            } else {
                repository.insertUser(UserEntity(email = email, password = password))
                onSuccess()
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUser(email, password)
            if (user != null) {
                onSuccess()
            } else {
                onError("Usuário ou senha incorretos!")
            }
        }
    }
}
