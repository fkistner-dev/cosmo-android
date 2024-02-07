package com.kilomobi.cosmo

import com.kilomobi.cosmo.data.BluetoothRepositoryImpl
import com.kilomobi.cosmo.domain.usecase.StartBluetoothScanUseCase
import com.kilomobi.cosmo.presentation.DummyContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBluetoothUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    // Setup useCase
    private val bluetoothScanner = BluetoothRepositoryImpl(
        FakeBluetoothScanner()
    )

    @Test
    fun getDevices_fromBluetooth() = scope.runTest {
        val useCase = StartBluetoothScanUseCase(bluetoothScanner)
        advanceUntilIdle()

        // Execute useCase
        val btDevices = DummyContent.getBluetoothList()
        val btDevicesReceived = useCase()
        advanceUntilIdle()

        // Assertion
        assert(btDevices == btDevicesReceived)
    }
}