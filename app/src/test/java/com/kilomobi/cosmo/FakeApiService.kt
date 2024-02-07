package com.kilomobi.cosmo

import com.kilomobi.cosmo.data.remote.CosmoApiService
import com.kilomobi.cosmo.presentation.DummyContent
import com.kilomobi.cosmo.presentation.list.RemoteDevices
import kotlinx.coroutines.delay

class FakeApiService : CosmoApiService {
    override suspend fun getDevices(): RemoteDevices {
        delay(1000)
        return DummyContent.getDevicesList()
    }
}
