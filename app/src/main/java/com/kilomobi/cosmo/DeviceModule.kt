package com.kilomobi.cosmo

import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitModule = module {
    factory { provideRetrofit() }
    single { provideCosmoApi(get()) }
    viewModel { ProductsViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://cosmo-api.develop-sr3snxi-x6u2x52ooksf4.de-2.platformsh.site/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
}

fun provideCosmoApi(retrofit: Retrofit): CosmoApiService =
    retrofit.create(CosmoApiService::class.java)