package com.kilomobi.cosmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kilomobi.cosmo.ui.theme.CosmoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val productsViewModel: ProductsViewModel by viewModel()

    companion object {
        const val DESTINATION_DEVICES_SCREEN = "destination_devices_screen"
        const val DESTINATION_DEVICE_DETAIL_SCREEN = "destination_device_detail_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CosmoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = DESTINATION_DEVICES_SCREEN
                ) {
                    composable(DESTINATION_DEVICES_SCREEN) {
                        DevicesScreen(
                            state = productsViewModel.state.value,
                            loadDevices = { productsViewModel.loadDevices() },
                            onDeviceClick = { device ->
                                DeviceHolder.selectedDevice = device
                                navController.navigate(route = DESTINATION_DEVICE_DETAIL_SCREEN)
                            }
                        )
                    }
                    composable(DESTINATION_DEVICE_DETAIL_SCREEN) {
                        DeviceDetailsScreen(
                            DeviceHolder.selectedDevice!!
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