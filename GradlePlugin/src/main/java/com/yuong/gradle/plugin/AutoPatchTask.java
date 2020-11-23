package com.yuong.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import javax.inject.Inject;

public class AutoPatchTask extends DefaultTask {
    private AutoPatchBean autoPatchBean;

    @Inject
    public AutoPatchTask(AutoPatchBean autoPatchBean) {
        this.autoPatchBean = autoPatchBean;
        System.out.println(this.autoPatchBean.toString());
        //任务分组
        setGroup("autopatch");
    }

    /**
     * 必须要加 @TaskAction 注解，不然编译器不知道是否是执行人物的方法
     * 命令：bsdiff oldapk newapk patch
     */
    @TaskAction
    public void executeTask() {
//        String oldApkPath = autoPatchBean.getOldApkPath();
//        File oldApkDir = new File(oldApkPath);
//        if (oldApkDir.exists() && oldApkDir.isDirectory()) {
//            String[] apks = oldApkDir.list(new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    if (name.endsWith("apk")) {
//                        return true;
//                    }
//                    return false;
//                }
//            });
////            File newApkFile = new File(autoPatchBean.getNewApkPath());
////            if (!newApkFile.exists()){
////                System.out.println("New Apk File not exists....");
////                return;
////            }
//            for (String oldApk : apks) {
//                final File oldApkFile = new File(oldApk);
//                final String patchPath = oldApkPath + File.separator + oldApkFile.getName().substring(0, oldApkFile.getName().lastIndexOf("."));
////                if (oldApkFile.exists()) {
//                    System.out.println("patchPath=" + patchPath);
//                    getProject().exec(new Action<ExecSpec>() {
//                        @Override
//                        public void execute(ExecSpec execSpec) {
//                            execSpec.commandLine("bsdiff",
//                                    autoPatchBean.getOldApkPath(),
//                                    autoPatchBean.getNewApkPath(),
//                                    autoPatchBean.getPatchPath());
//                        }
//                    });
////                }
//            }
//        }
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine(
                        "D:\\Android_BsDiff工具\\bsdiff.exe",
                        autoPatchBean.getOldApkPath(),
                        autoPatchBean.getNewApkPath(),
                        autoPatchBean.getPatchPath());
            }
        });
    }
}
