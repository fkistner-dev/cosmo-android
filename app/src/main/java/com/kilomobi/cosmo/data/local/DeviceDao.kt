package com.kilomobi.cosmo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices")
    suspend fun getAll(): List<LocalDevice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(devices: List<LocalDevice>)
}