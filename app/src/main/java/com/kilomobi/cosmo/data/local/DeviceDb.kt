package com.kilomobi.cosmo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalDevice::class],
    version = 1,
    exportSchema = false
)

abstract class DeviceDb : RoomDatabase() {
    abstract val dao: DeviceDao
}