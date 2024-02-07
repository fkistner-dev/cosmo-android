package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.R

class GetDeviceImageUseCase {
    operator fun invoke(model: String?): Int {
        return when (model) {
            "RIDE" -> {
                R.drawable.cosmo_ride
            }
            "REMOTE" -> {
                R.drawable.cosmo_remote_1
            }
            "VISION" -> {
                R.drawable.cosmo_vision
            }
            else -> {
                R.drawable.cosmo_logo
            }
        }
    }
}