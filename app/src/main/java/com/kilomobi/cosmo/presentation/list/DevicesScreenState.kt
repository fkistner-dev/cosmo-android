package com.kilomobi.cosmo.presentation.list

import com.kilomobi.cosmo.Device

data class DevicesScreenState(
    val devices: List<Device>,
    val isLoading: Boolean,
    val error: String? = null,
    val lightFiltering: Float,
)