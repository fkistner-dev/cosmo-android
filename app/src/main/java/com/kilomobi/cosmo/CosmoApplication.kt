package com.kilomobi.cosmo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CosmoApplication : ObjectApplication()

open class ObjectApplication  : Application()