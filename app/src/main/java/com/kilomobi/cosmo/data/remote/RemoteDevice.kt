package com.kilomobi.cosmo.data.remote

import com.squareup.moshi.Json

data class RemoteDevice(
    @Json(name = "macAddress")
    val macAddress: String,
    @Json(name = "model")
    val model: String?,
    @Json(name = "product")
    val product: String?,
    @Json(name = "firmwareVersion")
    val firmwareVersion: String,
    @Json(name = "serial")
    val serial: String?,
    @Json(name = "installationMode")
    val installationMode: String?,
    @Json(name = "brakeLight")
    val brakeLight: Boolean,
    @Json(name = "lightMode")
    val lightMode: String?,
    @Json(name = "lightAuto")
    val lightAuto: Boolean?,
    @Json(name = "lightValue")
    val lightValue: Int?
)