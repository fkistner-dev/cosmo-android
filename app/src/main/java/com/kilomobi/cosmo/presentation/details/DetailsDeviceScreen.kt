package com.kilomobi.cosmo.presentation.details

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.kilomobi.cosmo.domain.GetDeviceFeaturesUseCase
import com.kilomobi.cosmo.domain.GetDeviceImageUseCase
import com.kilomobi.cosmo.mockedDevice
import com.kilomobi.cosmo.presentation.list.DevicesScreen
import com.kilomobi.cosmo.presentation.list.DevicesScreenState
import com.kilomobi.cosmo.presentation.theme.CosmoTheme

@Composable
fun DetailsDeviceScreen(device: Device, onConnectClick: () -> Unit) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(MaterialTheme.shapes.medium)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.CosmoGrey))
    ) {
        Image(
            painter = rememberImagePainter(data = GetDeviceImageUseCase().invoke(device.model)),
            contentDescription = "image",
            modifier = imageModifier,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val features = GetDeviceFeaturesUseCase().invoke(device)
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.CosmoGreen)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {
                        onConnectClick()
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
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
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
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

data class DeviceDetailIcon(val icon: Int, @StringRes val text: Int)

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
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.White
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(deviceDetailIcons) { deviceDetailIcon ->
                Image(
                    painter = painterResource(id = deviceDetailIcon.icon),
                    contentDescription = stringResource(id = deviceDetailIcon.text),
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
            DeviceDetailIcon(
                R.drawable.baseline_event_seat_24,
                R.string.product_detail_feature_light_auto
            ),
            DeviceDetailIcon(
                R.drawable.baseline_headphones_battery_24,
                R.string.product_detail_feature_brake_light
            ),
            DeviceDetailIcon(
                R.drawable.baseline_bluetooth_searching_24,
                R.string.product_detail_feature_installation_mode
            ),
            DeviceDetailIcon(
                R.drawable.baseline_stop_circle_24,
                R.string.product_detail_feature_light_auto
            ),
        )
        DetailFeatureHorizontalList(deviceDetailIcons = deviceDetailIcons)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailDeviceScreenPreview() {
    CosmoTheme {
        val deviceDetailIcons = listOf(
            DeviceDetailIcon(
                R.drawable.baseline_event_seat_24,
                R.string.product_detail_feature_light_auto
            ),
            DeviceDetailIcon(
                R.drawable.baseline_headphones_battery_24,
                R.string.product_detail_feature_brake_light
            ),
            DeviceDetailIcon(
                R.drawable.baseline_bluetooth_searching_24,
                R.string.product_detail_feature_installation_mode
            ),
            DeviceDetailIcon(
                R.drawable.baseline_stop_circle_24,
                R.string.product_detail_feature_light_auto
            ),
        )
        DetailsDeviceScreen(
            device = mockedDevice, {}
        )
    }
}