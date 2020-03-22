#include <jni.h>
#include <string>
#include <__locale>

extern "C" {
    extern int main(int argc, const char *argv[]);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_bsdiffdemo2_BsPatcher_bsPatch(JNIEnv *env, jclass clazz, jstring old_apk,
                                               jstring patch, jstring new_apk) {

    const char* oldApk = env->GetStringUTFChars(old_apk, 0);
    const char* patch_ = env->GetStringUTFChars(patch, 0);
    const char* newApk = env->GetStringUTFChars(new_apk, 0);
    const char *argv[] = {"", oldApk, newApk, patch_};

    main(4, argv);

    env->ReleaseStringUTFChars(old_apk, oldApk);
    env->ReleaseStringUTFChars(patch, patch_);
    env->ReleaseStringUTFChars(new_apk, newApk);

}