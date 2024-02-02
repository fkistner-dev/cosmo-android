package com.kilomobi.cosmo

import android.content.Context
import com.polidea.rxandroidble3.RxBleClient
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
object CosmoModule {
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

    @Singleton
    @Provides
    fun provideBluetoothClient(context: Context): RxBleClient = RxBleClient.create(context)

}