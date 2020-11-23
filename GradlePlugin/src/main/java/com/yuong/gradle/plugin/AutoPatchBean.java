package com.yuong.gradle.plugin;

public class AutoPatchBean {
    //老的Apk路径
    private String oldApkPath;

    //新的apk路径
    private String newApkPath;

    //生成补丁包的路径
    private String patchPath;

    public String getOldApkPath() {
        return oldApkPath;
    }

    public void setOldApkPath(String oldApkPath) {
        this.oldApkPath = oldApkPath;
    }

    public String getNewApkPath() {
        return newApkPath;
    }

    public void setNewApkPath(String newApkPath) {
        this.newApkPath = newApkPath;
    }

    public String getPatchPath() {
        return patchPath;
    }

    public void setPatchPath(String patchPath) {
        this.patchPath = patchPath;
    }

    @Override
    public String toString() {
        return "AutoPatchBean{" +
                "oldApkPath='" + oldApkPath + '\'' +
                ", newApkPath='" + newApkPath + '\'' +
                ", patchPath='" + patchPath + '\'' +
                '}';
    }
}
