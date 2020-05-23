package com.example.derongliu.androidtest.camera;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.aglframework.smzh.AGLView;
import com.aglframework.smzh.camera.AGLCamera;
import com.example.derongliu.androidtest.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class CameraActivity extends Activity implements EasyPermissions.PermissionCallbacks {

    private AGLView aglView;
    private AGLCamera aglCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camea);

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, "该应用需要照相机权限和读写权限", 200, Manifest.permission.CAMERA);
        }
        aglView = findViewById(R.id.camera_preview);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            if (aglCamera == null) {
                aglCamera = new AGLCamera(aglView, 1080, 1920);
            }
            aglCamera.open();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (aglCamera != null) {
            aglCamera.close();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
