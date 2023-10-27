package com.oshanti.seersolution.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userId: Int,
    var password: String
)