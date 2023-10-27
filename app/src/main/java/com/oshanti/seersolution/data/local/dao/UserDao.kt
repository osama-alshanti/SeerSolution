package com.oshanti.seersolution.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oshanti.seersolution.data.model.User

@Dao
interface UserDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserById(userId: Int): User?


}