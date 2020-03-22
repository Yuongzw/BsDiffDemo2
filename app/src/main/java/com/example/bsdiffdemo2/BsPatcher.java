package com.example.bsdiffdemo2;

/**
 * @author : zhiwen.yang
 * date   : 2020/2/27
 * desc   :
 */
public class BsPatcher {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 合成安装包
     * @param oldApk    旧的apk路径
     * @param patch     补丁路径
     * @param newApk    新的安装包路径
     */
    public static native void bsPatch(String oldApk, String patch, String newApk);
}
