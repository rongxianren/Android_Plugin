package com.rongxianren.plugindemo

import android.app.Application
import com.rongxianren.plugindemo.utils.LoadUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LoadUtil.loadClass(this)
    }
}