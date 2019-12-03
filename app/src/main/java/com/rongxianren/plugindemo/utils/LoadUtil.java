package com.rongxianren.plugindemo.utils;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class LoadUtil {


    private final static String apkPath = "/sdcard/plugin.apk";

    public static void loadClass(Context context) {

        try {
            // BaseDexClassLoader 的 Class 对象
            Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");

            // 获取 pathList 的Field
            Field pathListField = clazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            // pathclassloader
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

            // 获取 宿主 pathList 的值
            Object hostPathList = pathListField.get(pathClassLoader);// pathClassLoader.pathList

            // 获取宿主的 dexElements
            Class<?> hostPathListClass = hostPathList.getClass();
            Field hostDexElementsField = hostPathListClass.getDeclaredField("dexElements");
            hostDexElementsField.setAccessible(true);
            Object[] hostDexElements = (Object[]) hostDexElementsField.get(hostPathList);


            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, pathClassLoader);
            Object pluginPathList = pathListField.get(dexClassLoader);

            // 获取插件的 dexElements
            Class<?> pluginPathListClass = pluginPathList.getClass();
            Field pluginDexElementsField = pluginPathListClass.getDeclaredField("dexElements");
            pluginDexElementsField.setAccessible(true);
            Object[] pluginDexElements = (Object[]) pluginDexElementsField.get(pluginPathList);


            // 创建数组
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, newDexElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements, hostDexElements.length, pluginDexElements.length);

            //  hostDexElements = newDexElements;
            hostDexElementsField.set(hostPathList, newDexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
