package com.kilomobi.cosmo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class LocalDevice(
    @PrimaryKey
    @ColumnInfo(name = "macAddress")
    val macAddress: String,
    @ColumnInfo(name = "model")
    val model: String?,
    @ColumnInfo(name = "product")
    val product: String?,
    @ColumnInfo(name = "firmwareVersion")
    val firmwareVersion: String,
    @ColumnInfo(name = "serial")
    val serial: String?,
    @ColumnInfo(name = "installationMode")
    val installationMode: String?,
    @ColumnInfo(name = "brakeLight")
    val brakeLight: Boolean,
    @ColumnInfo(name = "lightMode")
    val lightMode: String?,
    @ColumnInfo(name = "lightAuto")
    val lightAuto: Boolean?,
    @ColumnInfo(name = "lightValue")
    val lightValue: Int?
)
