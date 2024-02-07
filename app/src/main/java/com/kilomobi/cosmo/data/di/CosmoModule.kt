package com.kilomobi.cosmo.data.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.kilomobi.cosmo.data.remote.CosmoApiService
import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.data.CosmoBluetoothScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Singleton
    fun provideBluetoothAdapter(@ApplicationContext appContext: Context): BluetoothAdapter? {
        return (appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    @Provides
    @Singleton
    fun provideBluetoothScanner(
        bluetoothAdapter: BluetoothAdapter?
    ): BluetoothRepository {
        return CosmoBluetoothScanner(bluetoothAdapter)
    }
}