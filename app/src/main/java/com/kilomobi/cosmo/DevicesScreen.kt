package com.kilomobi.cosmo

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun DevicesScreen(
    state: DevicesScreenState, loadDevices: () -> Unit, onDeviceClick: (Device) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.cosmo_ride),
            contentDescription = "logo",
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .background(color = colorResource(id = R.color.CosmoGreen))
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.filter_title),
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(id = R.string.filter_name),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
                FilterSlider(state.lightFiltering) { }
            }
        }
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.CosmoGrey),
                trackColor = colorResource(id = R.color.CosmoGreen)
            )
        }
        if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center),
                )
                Button(
                    onClick = { loadDevices() },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = stringResource(id = R.string.common_retry))
                }
            }
        }
        if (state.devices.isNotEmpty()) {
            DeviceList(devices = state.devices, onDeviceClick)
        }
    }
}

@Composable
fun FilterSlider(
    lightFiltering: Float,
    onFilterValueChanged: (Float) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            imageVector = Icons.Outlined.List,
            contentDescription = "light-icon",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(40.dp)
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )
        Slider(
            value = lightFiltering,
            onValueChange = onFilterValueChanged,
            valueRange = 0f..100f,
            steps = 1,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.5f),
                activeTickColor = Color.White,
                inactiveTickColor = Color.White.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun DeviceList(devices: List<Device>, onClick: (Device) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.CosmoBlack))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.product_list_title),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
                    .padding(16.dp)
            ) {
                items(devices) { device ->
                    DeviceListItem(device = device, onClick)
                }
            }
        }
    }
}

@Composable
fun DeviceListItem(device: Device, onClick: (Device) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val transitionState = rememberUpdatedState(expanded)

    val scale = remember { Animatable(1f) }
    LaunchedEffect(transitionState) {
        scale.animateTo(
            targetValue = if (transitionState.value) 1.5f else 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(device) }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.CosmoGrey)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = R.drawable.cosmo_vision),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .scale(scale.value)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.product_detail_product) + device.product,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = stringResource(id = R.string.product_detail_model) + device.model,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}