package com.rongxianren.plugin.utils

import android.content.Context
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader

class LoadUtils {

    companion object {
        fun loadClass(context: Context) {


            val pathClassLoader = context.classLoader as PathClassLoader

            //var baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader")
            var baseDexClassLoader = BaseDexClassLoader::class.java
            val pathListField = baseDexClassLoader.getDeclaredField("pathList")
            pathListField.isAccessible = true

            val hostPathList = pathListField.get(pathClassLoader)

            val hostDexElementsField = hostPathList.javaClass.getDeclaredField("dexElements")
            hostDexElementsField.isAccessible = true
            val hostDexElements = hostDexElementsField.get(hostPathList) as Array<*>

            
        }
    }
}