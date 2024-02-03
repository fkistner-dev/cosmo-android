package com.kilomobi.cosmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kilomobi.cosmo.presentation.details.BluetoothViewModel
import com.kilomobi.cosmo.presentation.details.DetailsDeviceScreen
import com.kilomobi.cosmo.presentation.list.DevicesScreen
import com.kilomobi.cosmo.presentation.list.DevicesViewModel
import com.kilomobi.cosmo.presentation.theme.CosmoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val DESTINATION_DEVICES_SCREEN = "destination_devices_screen"
        const val DESTINATION_DEVICE_DETAIL_SCREEN = "destination_device_detail_screen"
        const val DESTINATION_BLUETOOTH_SCREEN = "destination_bluetooth_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CosmoTheme {
                val navController = rememberNavController()
                val devicesViewModel: DevicesViewModel = hiltViewModel()
                //val bluetoothViewModel: BluetoothViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = DESTINATION_DEVICES_SCREEN
                ) {
                    composable(DESTINATION_DEVICES_SCREEN) {
                        DevicesScreen(
                            state = devicesViewModel.state.value,
                            loadDevices = { devicesViewModel.loadDevices() },
                            onDeviceClick = { device ->
                                DeviceHolder.selectedDevice = device
                                navController.navigate(route = DESTINATION_DEVICE_DETAIL_SCREEN)
                            }
                        )
                    }
                    composable(DESTINATION_DEVICE_DETAIL_SCREEN) {
                        DetailsDeviceScreen(
                            device = DeviceHolder.selectedDevice!!,
                            onConnectClick = {
                                navController.navigate(route = DESTINATION_BLUETOOTH_SCREEN)
                            }
                        )
                    }
                }
            }
        }
    }

    object DeviceHolder {
        var selectedDevice: Device? = null
    }
}