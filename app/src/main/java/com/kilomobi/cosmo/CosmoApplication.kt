package com.kilomobi.cosmo

import android.app.Application
import com.polidea.rxandroidble3.exceptions.BleException
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@HiltAndroidApp
class CosmoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // ignore BleExceptions as they were surely delivered at least once
        // Please read https://github.com/Polidea/RxAndroidBle/wiki/FAQ:-UndeliverableException
        RxJavaPlugins.setErrorHandler { error ->
            if (error is UndeliverableException && error.cause is BleException) {
                return@setErrorHandler
            } else {
                throw Exception(error)
            }
        }
    }
}