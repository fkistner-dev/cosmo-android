package com.kilomobi.cosmo.presentation.details

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.BluetoothScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothRepository: BluetoothRepository,
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

    private val bluetoothScanResults = MutableLiveData<List<BluetoothDevice>>()

    fun startBluetoothScan() {
        viewModelScope.launch {
            try {
                val devices = bluetoothRepository.startBluetoothScan()
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
                bluetoothRepository.stopBluetoothScan()
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
