package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.Device
import com.kilomobi.cosmo.R
import com.kilomobi.cosmo.presentation.details.DeviceDetailIcon

class GetDeviceFeaturesUseCase {
    operator fun invoke(device: Device): List<DeviceDetailIcon> {
        val features = mutableListOf<DeviceDetailIcon>()
        device.installationMode?.let {
            features.add(
                DeviceDetailIcon(
                    icon = if (it.contentEquals("seat")) R.drawable.baseline_event_seat_24 else R.drawable.baseline_headphones_battery_24,
                    R.string.product_detail_feature_installation_mode
                )
            )
        }
        features.add(
            DeviceDetailIcon(
                icon = R.drawable.baseline_stop_circle_24,
                R.string.product_detail_feature_brake_light
            )
        )
        device.lightAuto?.let {
            features.add(
                DeviceDetailIcon(
                    icon = R.drawable.baseline_highlight_24,
                    R.string.product_detail_feature_light_auto
                )
            )
        }

        return features
    }
}