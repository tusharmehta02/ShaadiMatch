package com.shaadi.assignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaadi.assignment.data.model.local.ShaadiMatch

@Dao
interface ShaadiMatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ShaadiMatch>)

    @Query("SELECT * FROM shaadi_match")
    fun getAll(): List<ShaadiMatch>

    @Query("SELECT * FROM shaadi_match WHERE id=:id")
    fun getShaadiStatusById(id: String): ShaadiMatch?

    @Insert
    fun insert(shaadiMatch: ShaadiMatch)

    @Query("DELETE FROM shaadi_match")
    fun deleteAll()
}