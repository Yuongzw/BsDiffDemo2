package com.example.bsdiffdemo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.sample_text);
        tv.setText(getVerName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 1);
            }
        }
    }

    /**
     * 获取版本号名称
     * @return
     */
    public String getVerName() {
        String verName = "";
        try {
            verName = getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    @SuppressLint("StaticFieldLeak")
    public void update(View view) {
        new AsyncTask<Void, Void, File>() {

            @Override
            protected File doInBackground(Void... voids) {
                //获取旧版本的路径
                String oldApk = getApplicationInfo().sourceDir;
                //获取补丁包路径
                String patch = new File(Environment.getExternalStorageDirectory(), "patch").getAbsolutePath();
                String newApk = createNewApk().getAbsolutePath();
                //合成
                BsPatcher.bsPatch(oldApk, patch, newApk);
                return new File(newApk);
            }

            @Override
            protected void onPostExecute(File file) {
                //安装
                if (file != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(MainActivity.this,"com.example.bsdiffdemo2.fileprovider",file);
                        intent.setDataAndType(uri,"application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()),"application/vnd.android.package-archive");
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "差分包不存在！", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }

    /**
     * 创建合成后的apk文件
     * @return
     */
    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "bsdiff.apk");
        if (!newApk.exists()) {
            try {
                newApk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newApk;
    }
}
