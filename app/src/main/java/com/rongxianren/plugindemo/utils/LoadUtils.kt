package com.rongxianren.plugindemo.utils

import android.content.Context
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader

private val mApkPath = "sdcard/plugin.apk"

class LoadUtils {


    companion object {

        fun loadClass(context: Context) {

            try {
                val pathClassLoader = context.classLoader as PathClassLoader

                //var baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader")
                var baseDexClassLoader = BaseDexClassLoader::class.java
                val pathListField = baseDexClassLoader.getDeclaredField("pathList")
                pathListField.isAccessible = true


                val hostPathList = pathListField.get(pathClassLoader)
                val hostDexElementsField = hostPathList.javaClass.getDeclaredField("dexElements")
                hostDexElementsField.isAccessible = true

                ///获取宿主dexElements
                val hostDexElements = hostDexElementsField.get(hostPathList) as Array<Any?>


                val dexClassLoader = DexClassLoader(
                    mApkPath,
                    context.cacheDir.absolutePath,
                    null,
                    pathClassLoader.parent
                )
                val pluginPathList = pathListField.get(dexClassLoader)
                val pluginDexElementsField =
                    pluginPathList.javaClass.getDeclaredField("dexElements")
                pluginDexElementsField.isAccessible = true

                ///获取插件dexElements
                val pluginDexElements = pluginDexElementsField.get(pluginPathList) as Array<Any?>


                val mergeDexElements =
                    arrayOfNulls<Any?>(hostDexElements.size + pluginDexElements.size)

                ///合并宿主 dexElements 和插件 dexElements
                System.arraycopy(hostDexElements, 0, mergeDexElements, 0, hostDexElements.size)
                System.arraycopy(
                    pluginDexElements,
                    0,
                    mergeDexElements,
                    hostDexElements.size,
                    pluginDexElements.size
                )


                ///替换宿主 dexElements
                hostDexElementsField.set(hostPathList, mergeDexElements)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}