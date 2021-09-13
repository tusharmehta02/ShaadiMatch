package com.shaadi.assignment.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shaadi_match")
data class ShaadiMatch(
    @PrimaryKey
    val id: String,
    val status: String?
)
