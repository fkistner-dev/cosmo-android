package com.kilomobi.cosmo.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kilomobi.cosmo.R
import com.kilomobi.cosmo.domain.usecase.GetPermissionsUseCase
import com.kilomobi.cosmo.presentation.bluetooth.BluetoothScreen
import com.kilomobi.cosmo.presentation.details.BluetoothViewModel
import com.kilomobi.cosmo.presentation.details.DetailsDeviceScreen
import com.kilomobi.cosmo.data.remote.RemoteDevice
import com.kilomobi.cosmo.presentation.list.DevicesScreen
import com.kilomobi.cosmo.presentation.list.DevicesViewModel
import com.kilomobi.cosmo.presentation.theme.CosmoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            handlePermissionsResult(permissions)
        }

    companion object {
        const val DESTINATION_DEVICES_SCREEN = "destination_devices_screen"
        const val DESTINATION_DEVICE_DETAIL_SCREEN = "destination_device_detail_screen"
        const val DESTINATION_BLUETOOTH_SCREEN = "destination_bluetooth_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions = GetPermissionsUseCase().invoke()
        permissionLauncher.launch(permissions)

        setContent {
            CosmoTheme {
                val navController = rememberNavController()
                val devicesViewModel: DevicesViewModel = hiltViewModel()
                val bluetoothViewModel: BluetoothViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = DESTINATION_DEVICES_SCREEN
                ) {
                    composable(DESTINATION_DEVICES_SCREEN) {
                        DevicesScreen(
                            state = devicesViewModel.state.value,
                            loadDevices = { devicesViewModel.loadDevices() },
                            onDeviceClick = { device ->
                                ActivityHelper.selectedDevice = device
                                navController.navigate(route = DESTINATION_DEVICE_DETAIL_SCREEN)
                            },
                            onFilterValueChanged = { devicesViewModel.updateFilterValue(it) }
                        )
                    }
                    composable(DESTINATION_DEVICE_DETAIL_SCREEN) {
                        DetailsDeviceScreen(
                            device = ActivityHelper.selectedDevice!!,
                            onConnectClick = {
                                if (ActivityHelper.permissionsGranted) navController.navigate(route = DESTINATION_BLUETOOTH_SCREEN)
                                else permissionLauncher.launch(permissions)
                            }
                        )
                    }
                    composable(DESTINATION_BLUETOOTH_SCREEN) {
                        BluetoothScreen(
                            state = bluetoothViewModel.state.value,
                            onPermissionRequest = {
                                bluetoothViewModel.startBluetoothScan()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun handlePermissionsResult(permissions: Map<String, Boolean>) {
        var globalPermission = true
        for ((_, isGranted) in permissions) {
            if (!isGranted) {
                globalPermission = false
            }
        }

        if (globalPermission) ActivityHelper.permissionsGranted = true
        else Toast.makeText(
            applicationContext,
            applicationContext.getText(R.string.common_permissions_denied),
            Toast.LENGTH_LONG
        ).show()
    }

    object ActivityHelper {
        var selectedDevice: CosmoDevice? = null
        var permissionsGranted: Boolean = false
    }
}