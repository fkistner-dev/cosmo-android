package com.kilomobi.cosmo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "device")
data class Device(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("deviceId")
    val deviceId: Int = 0,
    @ColumnInfo("macAddress")
    @Json(name = "macAddress")
    val macAddress: String,
    @ColumnInfo("model")
    @Json(name = "model")
    val model: String?,
    @ColumnInfo("product")
    @Json(name = "product")
    val product: String?,
    @ColumnInfo(name = "firmwareVersion")
    @Json(name = "firmwareVersion")
    val firmwareVersion: String,
    @ColumnInfo(name = "serial")
    @Json(name = "serial")
    val serial: String?,
    @ColumnInfo(name = "installationMode")
    @Json(name = "installationMode")
    val installationMode: String?,
    @ColumnInfo(name = "brakeLight")
    @Json(name = "brakeLight")
    val brakeLight: Boolean,
    @ColumnInfo(name = "lightMode")
    @Json(name = "lightMode")
    val lightMode: String?,
    @ColumnInfo(name = "lightAuto")
    @Json(name = "lightAuto")
    val lightAuto: Boolean?,
    @ColumnInfo(name = "lightValue")
    @Json(name = "lightValue")
    val lightValue: Int?
)

val mockedDevice = Device(deviceId=0, macAddress="4921201e38d5", model="RIDE", product="RIDE", firmwareVersion="2.2.2", serial="BC892C9C-047D-8402-A9FD-7B2CC0048736", installationMode="helmet", brakeLight=false, lightMode="OFF", lightAuto=false, lightValue=0)