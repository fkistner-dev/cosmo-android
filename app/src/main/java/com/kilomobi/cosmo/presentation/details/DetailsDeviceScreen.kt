package com.kilomobi.cosmo.presentation.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.kilomobi.cosmo.Device
import com.kilomobi.cosmo.R
import com.kilomobi.cosmo.presentation.theme.CosmoTheme

@Composable
fun DetailsDeviceScreen(device: Device) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .clip(MaterialTheme.shapes.medium)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.CosmoGrey))
    ) {
        Image(
            painter = rememberImagePainter(data = R.drawable.cosmo_vision),
            contentDescription = "image",
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // TODO : UseCase to obtain DeviceDetail from 'device'
            val features = mutableListOf<DeviceDetailIcon>()
            device.installationMode?.let {
                features.add(
                    DeviceDetailIcon(
                        icon = if (it.contentEquals("seat")) R.drawable.baseline_event_seat_24 else R.drawable.baseline_headphones_battery_24,
                        stringResource(id = R.string.product_detail_feature_installation_mode)
                    )
                )
            }
            features.add(
                DeviceDetailIcon(
                    icon = R.drawable.baseline_stop_circle_24,
                    stringResource(id = R.string.product_detail_feature_brake_light)
                )
            )
            device.lightAuto?.let {
                features.add(
                    DeviceDetailIcon(
                        icon = R.drawable.baseline_highlight_24,
                        stringResource(id = R.string.product_detail_feature_light_auto)
                    )
                )
            }
            DetailFeatureHorizontalList(features)
            DetailText(
                text = stringResource(id = R.string.product_detail_model),
                value = device.model
            )
            DetailText(
                text = stringResource(id = R.string.product_detail_product),
                value = device.product
            )
            DetailText(
                text = stringResource(id = R.string.product_detail_version),
                value = device.firmwareVersion
            )
            DetailText(
                text = stringResource(id = R.string.product_detail_mac),
                value = device.macAddress
            )
            DetailText(
                text = stringResource(id = R.string.product_detail_bluetooth), stringResource(
                    id = R.string.product_detail_bluetooth_hint
                )
            )
            Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.CosmoGreen)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = {

                }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_bluetooth_searching_24),
                    contentDescription = "icon",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.product_detail_bluetooth_connect),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun DetailText(text: String, value: String?) {
    if (value?.isNotEmpty() == true) Text(
        text = "$text $value",
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}

data class DeviceDetailIcon(val icon: Int, val text: String)

@Composable
fun DetailFeatureHorizontalList(deviceDetailIcons: List<DeviceDetailIcon>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.product_detail_features),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.White
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(deviceDetailIcons) { deviceDetailIcon ->
                Image(
                    painter = painterResource(id = deviceDetailIcon.icon),
                    contentDescription = deviceDetailIcon.text,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            Toast
                                .makeText(context, deviceDetailIcon.text, Toast.LENGTH_LONG)
                                .show()
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailFeatureHorizontalListPreview() {
    CosmoTheme {
        val deviceDetailIcons = listOf(
            DeviceDetailIcon(R.drawable.baseline_event_seat_24, "Seat"),
            DeviceDetailIcon(R.drawable.baseline_headphones_battery_24, "Helmet"),
            DeviceDetailIcon(R.drawable.baseline_bluetooth_searching_24, "Bluetooth"),
            DeviceDetailIcon(R.drawable.baseline_stop_circle_24, "Brake"),
        )
        DetailFeatureHorizontalList(deviceDetailIcons = deviceDetailIcons)
    }
}