package com.kilomobi.cosmo.presentation.bluetooth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kilomobi.cosmo.R

@Composable
fun BluetoothScreen(
    state: BluetoothScreenState,
    onPermissionRequest: () -> Unit,
    onStopAction: () -> Unit
) {
    LaunchedEffect(key1 = "bluetooth_permissions") {
        onPermissionRequest()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isSearching) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.CosmoGreen),
                    modifier = Modifier.size(48.dp).align(Alignment.CenterHorizontally)
                )
                Button(
                    onClick = { onStopAction() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = com.kilomobi.cosmo.features.R.color.CosmoGreen)),
                    modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(id = R.string.bluetooth_scan_stop),
                        color = Color.White
                    )
                }
            }
            state.error?.let { error ->
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.bluetooth_devices_list),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            if (state.bluetoothDevices.isNotEmpty()) BluetoothDeviceList(state.bluetoothDevices)
            if (!state.isSearching && state.bluetoothDevices.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.bluetooth_no_devices),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun BluetoothDeviceList(devices: List<CosmoListItemDevice>) {
    LazyColumn {
        items(devices) { device ->
            BluetoothDeviceItem(device = device)
        }
    }
}

@Composable
fun BluetoothDeviceItem(device: CosmoListItemDevice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = device.name ?: "Unknown", fontWeight = FontWeight.Bold)
        Text(text = device.address)
    }
}

@Preview
@Composable
fun PreviewBluetoothDeviceList() {
    val devices = List(10) {
        CosmoListItemDevice("Device $it", "00:00:00:00:22:22")
    }
    BluetoothDeviceList(devices = devices)
}

@Preview
@Composable
fun PreviewBluetoothDeviceItem() {
    val device = CosmoListItemDevice("Device", "00:00:00:00:11:11")
    BluetoothDeviceItem(device = device)
}

data class CosmoListItemDevice(val name: String?, val address: String)