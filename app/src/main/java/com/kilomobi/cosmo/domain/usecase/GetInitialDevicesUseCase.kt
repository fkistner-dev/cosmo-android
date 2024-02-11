package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.data.DevicesRepository
import com.kilomobi.cosmo.domain.model.CosmoDevice
import javax.inject.Inject

class GetInitialDevicesUseCase @Inject constructor(
    private val repository: DevicesRepository
) {
    suspend operator fun invoke(): List<CosmoDevice> {
        repository.loadDevices()
        return repository.getDevices()
    }
}