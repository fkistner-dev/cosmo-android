package com.kilomobi.cosmo.presentation.bluetooth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kilomobi.cosmo.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BluetoothScreen(state: BluetoothScreenState, onPermissionRequest: () -> Unit) {
    LaunchedEffect(key1 = "bluetooth_permissions") {
        onPermissionRequest()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isSearching) {
                CountdownTimerText()
                CircularProgressIndicator(
                    progress = 0.9f,
                    color = colorResource(id = R.color.CosmoGreen),
                    modifier = Modifier.size(48.dp)
                )
            }
            state.error?.let { error ->
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (state.bluetoothDevices.isNotEmpty()) {
                BluetoothDeviceList(state.bluetoothDevices.map {
                    CosmoListItemDevice(it.name, it.address)
                })
            }
        }
    }
}

@Composable
fun CountdownTimerText() {
    var time by remember { mutableIntStateOf(10) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch {
            while (time > 0) {
                delay(1000)
                time--
            }
        }

        onDispose {
            job.cancel()
        }
    }

    Text(
        text = "Recherche des appareils... $time",
        style = MaterialTheme.typography.bodyLarge,
        color = colorResource(id = R.color.CosmoGreen),
        modifier = Modifier
            .padding(16.dp)
    )
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