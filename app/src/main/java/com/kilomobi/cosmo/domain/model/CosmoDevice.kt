package com.kilomobi.cosmo.domain.model

data class CosmoDevice(
    val macAddress: String,
    val model: String?,
    val product: String?,
    val firmwareVersion: String,
    val installationMode: String?,
    val brakeLight: Boolean,
    val lightMode: String?,
    val lightAuto: Boolean?,
    val lightValue: Int?
)