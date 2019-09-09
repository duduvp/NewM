package br.com.newm.teste.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val birthDate: String,
    val cpf: String,
    val phone: String,
    val email: String,
    val address: String,
    val obs: String
)