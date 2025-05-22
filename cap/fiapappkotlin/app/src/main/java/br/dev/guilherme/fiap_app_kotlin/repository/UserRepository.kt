package br.dev.guilherme.fiap_app_kotlin.repository

import br.dev.guilherme.fiap_app_kotlin.data.local.UserDao
import br.dev.guilherme.fiap_app_kotlin.data.local.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUser(email: String, password: String): UserEntity? {
        return userDao.getUser(email, password)
    }
}
