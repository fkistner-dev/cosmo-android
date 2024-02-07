package com.kilomobi.cosmo

import com.kilomobi.cosmo.data.DevicesRepository
import com.kilomobi.cosmo.domain.usecase.GetDeviceFeaturesUseCase
import com.kilomobi.cosmo.domain.usecase.GetDeviceImageUseCase
import com.kilomobi.cosmo.presentation.DummyContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDeviceUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    // Setup useCase
    private val devicesRepository = DevicesRepository(
        FakeApiService(),
        dispatcher
    )

    @Test
    fun getDevice_getFeatures() = scope.runTest {
        devicesRepository.loadDevices()
        val useCase = GetDeviceFeaturesUseCase()
        advanceUntilIdle()

        // Execute useCase
        val features = DummyContent.getFeatures()
        val featuresReceived = useCase(DummyContent.getDevice())
        advanceUntilIdle()

        // Assertion
        assert(features == featuresReceived)
    }

    @Test
    fun getDevice_getImage() = scope.runTest {
        devicesRepository.loadDevices()
        val useCase = GetDeviceImageUseCase()
        advanceUntilIdle()

        // Execute useCase
        val drawableRes = R.drawable.cosmo_remote_1
        val drawableReceived = useCase("REMOTE")
        advanceUntilIdle()

        // Assertion
        assert(drawableRes == drawableReceived)
    }
}