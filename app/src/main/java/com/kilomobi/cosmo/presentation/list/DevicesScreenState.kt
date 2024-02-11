package com.kilomobi.cosmo.presentation.list

import com.kilomobi.cosmo.domain.model.CosmoDevice

data class DevicesScreenState(
    val devices: List<CosmoDevice>,
    val isLoading: Boolean,
    val error: String? = null,
    val lightFiltering: Float,
)