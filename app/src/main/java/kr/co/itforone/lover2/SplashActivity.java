package kr.co.itforone.lover2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this.getBaseContext();

        Log.d("로그", "splash onCreate");

        checkPermissions();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            List<String> permissions = new ArrayList<>();

            // 기본 권한
            permissions.add(Manifest.permission.CAMERA);
            // permissions.add(Manifest.permission.READ_PHONE_STATE);

            // 안드로이드 13 이상일 경우
            if (Build.VERSION.SDK_INT >= 33) { // Android 13
                permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
                // permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
                // permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
            } else {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE); // 기기, 사진, 미디어, 파일 엑세스 권한
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            // Log.d("로그 VERSION.SDK_INT", (Build.VERSION.SDK_INT) + "");

            String[] permissionArray = permissions.toArray(new String[0]);

            TedPermission.with(context)
                    .setPermissionListener(permissionlistener)
                    //.setRationaleMessage("앱 사용을 위해 권한을 허용해 주세요.")
                    .setDeniedMessage("권한 요청에 동의 해주셔야 이용 가능합니다.\n설정 - 권한에서 허용 하시기 바랍니다.")
                    .setGotoSettingButton(true)
                    .setPermissions(permissionArray)
                    .check();

        } else {
            initView();
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            initView(); // 권한이 승인되었을 때 실행할 함수
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(context, "권한 요청에 동의 해주셔야 이용 가능합니다.\n설정 - 권한에서 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();

            // 거부된 권한들이 있는 경우 실행되는 메서드
            for (String permission : deniedPermissions) {
                Log.d("로그 Denied Permission", permission);
            }

            // 앱종료
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };

    private void initView() {
        // 핸들러로 이용해서 1초간 머물고 이동이 됨
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

}