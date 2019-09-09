package br.com.newm.teste.data.local.dao

import androidx.room.*
import br.com.newm.teste.data.local.entity.Customer

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(customer: Customer)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(customer: Customer)

    @Query("SELECT * FROM customer WHERE name GLOB :str OR email GLOB :str ORDER BY name LIMIT 10")
    suspend fun findByNameOrEmail(str: String): List<Customer>

    @Query("SELECT * FROM customer ORDER BY id DESC LIMIT 10")
    suspend fun queryAll(): List<Customer>

    @Delete
    suspend fun delete(customer: Customer?)

}