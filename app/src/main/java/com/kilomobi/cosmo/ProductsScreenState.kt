package com.kilomobi.cosmo

data class ProductsScreenState(
    val devices: List<Device>,
    val isLoading: Boolean,
    val error: String? = null,
    val lightFiltering: Float
)