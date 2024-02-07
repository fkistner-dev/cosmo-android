package com.kilomobi.cosmo.presentation.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.domain.usecase.StartBluetoothScanUseCase
import com.kilomobi.cosmo.presentation.bluetooth.BluetoothScreenState
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothScanner: BluetoothRepository,
    private val startBluetoothScanUseCase: StartBluetoothScanUseCase
) : ViewModel() {

    private val _state = mutableStateOf(
        BluetoothScreenState(
            permissionGranted = false,
            bluetoothDevices = emptyList(),
            isSearching = true,
            error = null
        )
    )

    val state: State<BluetoothScreenState>
        get() = _state

    private val bluetoothScanResults = MutableLiveData<List<CosmoListItemDevice>>()

    fun startBluetoothScan() {
        viewModelScope.launch {
            try {
                val devices = startBluetoothScanUseCase()
                bluetoothScanResults.value = devices
                _state.value = _state.value.copy(
                    bluetoothDevices = devices.toList(),
                    isSearching = false
                )
                stopBluetoothScan()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isSearching = false
                )
                Log.d(BluetoothViewModel::class.java.name, "Error caught while starting scan ${e.message}")
            }
        }
    }

    private fun stopBluetoothScan() {
        viewModelScope.launch {
            try {
                bluetoothScanner.stopScan()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isSearching = false
                )
                Log.d(BluetoothViewModel::class.java.name, "Error caught while stopping scan ${e.message}")
            }
        }
    }
}
