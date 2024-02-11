package com.kilomobi.cosmo.features.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PermissionViewModel : ViewModel() {

    private val _state = mutableStateOf(
        PermissionScreenState(
            permission = PermissionState.Requested,
            text = null,
            image = null
        )
    )

    val state: State<PermissionScreenState>
        get() = _state

    fun setPermission(text: Int, image: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                text = text,
                image = image
            )
        }
    }
}

sealed class PermissionState {
    data object Granted : PermissionState()
    data object Refused : PermissionState()
    data object Requested : PermissionState()
}
