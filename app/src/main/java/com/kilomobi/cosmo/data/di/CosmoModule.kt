package com.kilomobi.cosmo.data.di

import android.bluetooth.BluetoothAdapter
import com.kilomobi.cosmo.data.BluetoothRepository
import com.kilomobi.cosmo.data.BluetoothRepositoryImpl
import com.kilomobi.cosmo.data.remote.CosmoApiService
import com.kilomobi.cosmo.domain.BluetoothScanner
import com.kilomobi.cosmo.domain.CosmoBluetoothScanner
import com.kilomobi.cosmo.presentation.details.BluetoothViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cosmo-api.develop-sr3snxi-x6u2x52ooksf4.de-2.platformsh.site/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Singleton
    @Provides
    fun provideCosmoApi(retrofit: Retrofit): CosmoApiService =
        retrofit.create(CosmoApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {
    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter? {
        return BluetoothAdapter.getDefaultAdapter()
    }

    @Provides
    fun provideBluetoothScanner(
        bluetoothAdapter: BluetoothAdapter?
    ): BluetoothScanner {
        return CosmoBluetoothScanner(bluetoothAdapter)
    }

    @Provides
    fun provideBluetoothRepository(
        bluetoothScanner: BluetoothScanner
    ): BluetoothRepository {
        return BluetoothRepositoryImpl(bluetoothScanner)
    }

    @Provides
    fun provideBluetoothViewModel(
        bluetoothRepository: BluetoothRepository,
    ): BluetoothViewModel {
        return BluetoothViewModel(bluetoothRepository)
    }
}