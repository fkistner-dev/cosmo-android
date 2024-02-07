package com.kilomobi.cosmo.presentation.list

import com.kilomobi.cosmo.data.remote.RemoteDevice

data class DevicesScreenState(
    val devices: List<RemoteDevice>,
    val isLoading: Boolean,
    val error: String? = null,
    val lightFiltering: Float,
)