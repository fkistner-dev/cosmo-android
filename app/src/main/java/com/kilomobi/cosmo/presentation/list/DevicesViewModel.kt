package com.kilomobi.cosmo.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.data.di.IoDispatcher
import com.kilomobi.cosmo.domain.usecase.GetDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val getDevicesUseCase: GetDevicesUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
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
            val remoteList = getDevicesUseCase()
            _state.value = _state.value.copy(
                devices = remoteList,
                isLoading = false,
                error = null
            )
        }
    }

    fun updateFilterValue(filterValue: Float) {
        _state.value = _state.value.copy(
            lightFiltering = filterValue
        )
    }
}