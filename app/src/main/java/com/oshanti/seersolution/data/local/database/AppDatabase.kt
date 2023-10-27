package com.oshanti.seersolution.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oshanti.seersolution.data.local.dao.UserDao
import com.oshanti.seersolution.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}