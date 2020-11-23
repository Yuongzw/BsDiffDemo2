package com.yuong.gradle.plugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

/**
 * 自动打差分包插件
 */
public class AutoPatchPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        //第一个参数是拓展名称，第二个参数是接受的Java Bean类
        final AutoPatchBean autoPatch = project.getExtensions().create("autoPatch", AutoPatchBean.class);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {
                System.out.println(autoPatch.toString());
                //动态获取当前编译目标的apk文件 debug 还是release
                AppExtension appExtension = (AppExtension) project.getExtensions().getByName("android");
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        //debug 或者 release
                        final String variantName = applicationVariant.getName();
                        System.out.println("variantName=" + variantName);
                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
                                //apk文件
                                File outputFile = baseVariantOutput.getOutputFile();
//                                autoPatch.setNewApkPath(outputFile.getAbsolutePath());
                                //创建人物
                                project.getTasks().create("autoPatch" + variantName,
                                        AutoPatchTask.class,autoPatch);
                            }
                        });
                    }
                });

            }
        });
    }
}
