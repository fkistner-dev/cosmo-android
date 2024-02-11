package com.kilomobi.cosmo.presentation

import com.kilomobi.cosmo.R
import com.kilomobi.cosmo.data.remote.RemoteDevice
import com.kilomobi.cosmo.domain.model.CosmoDevice
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import com.kilomobi.cosmo.presentation.details.DeviceDetailIcon
import com.kilomobi.cosmo.presentation.list.RemoteDevices

object MockContent {
    fun getDevice() = CosmoDevice(macAddress="4921201e38d5", model="RIDE", product="RIDE", firmwareVersion="2.2.2", installationMode="helmet", brakeLight=false, lightMode="OFF", lightAuto=false, lightValue=0)
    fun getDevicesList(): RemoteDevices {
        return RemoteDevices(List(10) {
            RemoteDevice(
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

    fun getBluetoothList(): List<CosmoListItemDevice> {
        return listOf(CosmoListItemDevice("bt1", "00:00:00:00:11"), CosmoListItemDevice("bt2", "00:00:00:00:22"))
    }
}
