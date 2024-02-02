package com.kilomobi.cosmo

data class DevicesScreenState(
    val devices: List<Device>,
    val isLoading: Boolean,
    val error: String? = null,
    val lightFiltering: Float,
)