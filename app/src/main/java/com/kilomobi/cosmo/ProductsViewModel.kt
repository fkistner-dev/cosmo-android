package com.kilomobi.cosmo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val restInterface: CosmoApiService
) : ViewModel() {
    private val _state = mutableStateOf(
        ProductsScreenState(
            devices = emptyList(),
            isLoading = true,
            lightFiltering = 100f
        )
    )

    val state: State<ProductsScreenState>
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

    private fun loadDevices() {
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
}