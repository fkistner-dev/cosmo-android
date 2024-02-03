package com.kilomobi.cosmo

import com.kilomobi.cosmo.presentation.details.Device
import com.kilomobi.cosmo.presentation.details.DeviceDetailIcon
import com.kilomobi.cosmo.presentation.list.RemoteDevices

object DummyContent {
    fun getDevice() = Device(deviceId=0, macAddress="4921201e38d5", model="RIDE", product="RIDE", firmwareVersion="2.2.2", serial="BC892C9C-047D-8402-A9FD-7B2CC0048736", installationMode="helmet", brakeLight=false, lightMode="OFF", lightAuto=false, lightValue=0)
    fun getDevicesList(): RemoteDevices {
        return RemoteDevices(List(10) {
            Device(
                deviceId = it,
                macAddress = "MAC_$it",
                model = "MODEL_$it",
                product = "PRODUCT_$it",
                firmwareVersion = "1.0",
                serial = "SERIAL_$it",
                installationMode = "helmet",
                brakeLight = false,
                lightMode = "OFF",
                lightAuto = false,
                lightValue = it * 10
            )
        })
    }

    fun getFeatures(): List<DeviceDetailIcon> {
        val features = mutableListOf<DeviceDetailIcon>()
        features.add(
            DeviceDetailIcon(
                icon = R.drawable.baseline_headphones_battery_24,
                R.string.product_detail_feature_installation_mode
            )
        )
        features.add(
            DeviceDetailIcon(
                icon = R.drawable.baseline_stop_circle_24,
                R.string.product_detail_feature_brake_light
            )
        )

        return features
    }
}
