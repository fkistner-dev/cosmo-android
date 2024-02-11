package com.kilomobi.cosmo.presentation.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.domain.usecase.StartBluetoothScanUseCase
import com.kilomobi.cosmo.domain.usecase.StopBluetoothScanUseCase
import com.kilomobi.cosmo.presentation.bluetooth.BluetoothScreenState
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val startBluetoothScanUseCase: StartBluetoothScanUseCase,
    private val stopBluetoothScanUseCase: StopBluetoothScanUseCase
) : ViewModel() {

    private val _state = mutableStateOf(
        BluetoothScreenState(
            permissionGranted = false,
            bluetoothDevices = emptyList(),
            isSearching = true,
            error = null
        )
    )

    private val _bluetoothDevices = mutableListOf<CosmoListItemDevice>()

    val state: State<BluetoothScreenState>
        get() = _state

    fun startBluetoothScan() {
        viewModelScope.launch {
            startBluetoothScanUseCase().collect { device ->
                _bluetoothDevices.add(device)
                _state.value = _state.value.copy(
                    bluetoothDevices = _bluetoothDevices,
                )
            }
        }
    }

    fun stopBluetoothScan() {
        viewModelScope.launch {
            try {
                stopBluetoothScanUseCase()
                _state.value = _state.value.copy(
                    isSearching = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isSearching = false
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            stopBluetoothScanUseCase()
        }
    }
}
