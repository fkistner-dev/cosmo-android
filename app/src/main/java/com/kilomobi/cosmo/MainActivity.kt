package com.kilomobi.cosmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kilomobi.cosmo.ui.theme.CosmoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ProductsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CosmoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductScreen(
                        state = viewModel.state.value,
                    )
                }
            }
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
fun ProductScreen(state: ProductsScreenState) {
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
                .background(Color(android.graphics.Color.parseColor("#9AC1A8")))
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
        DeviceList(devices = state.devices)
    }
}

@Composable
fun DeviceList(devices: List<Device>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
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
                    DeviceListItem(device = device)
                }
            }
        }
    }
}

@Composable
fun DeviceListItem(device: Device) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2A2A2A)
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
                    painter = painterResource(id = R.drawable.cosmo_vision),
                    contentDescription = "product-image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "${stringResource(id = R.string.product_detail_product)} : ${device.model}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${stringResource(id = R.string.product_detail_model)} : ${device.product}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "forward-icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    CosmoTheme {
        ProductScreen(
            state = ProductsScreenState(listOf(), false, null, 100f)
        )
    }
}

