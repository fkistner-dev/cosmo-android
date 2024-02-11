package com.kilomobi.cosmo.features.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PermissionScreenState(
    val permission: PermissionState,
    @StringRes val text: Int?,
    @DrawableRes val image: Int?
)
