package com.kilomobi.cosmo.features.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kilomobi.cosmo.features.R

@Composable
fun PermissionScreen(
    state: PermissionScreenState,
    onPermissionRequest: () -> Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = state.image!!),
            contentDescription = "permission-image",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp),
            colorFilter = ColorFilter.tint(Color.Black),
        )
        Text(
            text = stringResource(id = state.text!!),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(
            onClick = { onPermissionRequest() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.CosmoGreen)),
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = stringResource(id = R.string.common_ok), color = Color.White)
        }
    }
}

@Preview
@Composable
fun PermissionScreenPreview() {
    PermissionScreen(
        state = PermissionScreenState(
            PermissionState.Requested,
            R.string.permission_bluetooth_text,
            R.drawable.baseline_bluetooth_searching_24
        )
    ) { true }
}
