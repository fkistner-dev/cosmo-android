package com.kilomobi.cosmo.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.CosmoApiService
import com.kilomobi.cosmo.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val restInterface: CosmoApiService
) : ViewModel() {
    private val _state = mutableStateOf(
        DevicesScreenState(
            devices = emptyList(),
            isLoading = true,
            lightFiltering = 100f
        )
    )

    val state: State<DevicesScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }

    init {
        loadDevices()
    }

    fun loadDevices() {
        viewModelScope.launch(errorHandler) {
            val remoteList = getRemoteDevices()
            _state.value = _state.value.copy(
                devices = remoteList,
                isLoading = false,
                error = null,
                lightFiltering = 100f
            )
        }
    }

    private suspend fun getRemoteDevices(): List<Device> {
        return withContext(Dispatchers.IO) {
            restInterface.getDevices().devices
        }
    }

    fun updateFilterValue(filterValue: Float) {
        _state.value = _state.value.copy(
            lightFiltering = filterValue
        )
    }
}