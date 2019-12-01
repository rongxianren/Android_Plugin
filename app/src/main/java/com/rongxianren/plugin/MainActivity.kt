package com.rongxianren.plugin

import android.app.Activity
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import dalvik.system.DexClassLoader

class MainActivity : AppCompatActivity() {


    val TAG = "ANDROID_PLUGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, this.classLoader.javaClass.toString())

        var tempClassLoader = classLoader
        while (tempClassLoader.parent != null) {
            tempClassLoader = tempClassLoader.parent
            Log.d(TAG, tempClassLoader.javaClass.toString())
        }


        Log.d(TAG, tempClassLoader.javaClass.simpleName)


        Log.d(TAG, Activity::class.java.classLoader.toString())

        Log.d(TAG, TextView::class.java.classLoader.toString())

        Log.d(TAG, AppCompatActivity::class.java.classLoader.toString())

//        ClassLoader
        var intent:Intent = Intent()

//        intent.setComponent()
//
//        AssetManager
    }
}
