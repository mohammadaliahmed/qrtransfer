package qrscanner.application.Activites;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qrscanner.application.R;
import qrscanner.application.Utils.CommonUtils;


public class Splash extends Activity {

    ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        mScannerView = findViewById(R.id.scanner_view);

        getPermissions();
    }

    private void getPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,

        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            startScanner();
        }
    }

    private void startScanner() {

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setAutoFocus(true);
        mScannerView.startCamera(0);
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                startActivity(new Intent(Splash.this, Results.class)
                        .putExtra("result", result.getText())
                        .putExtra("type", result.getBarcodeFormat().toString())
                );
                finish();

            }
        });
    }


    public boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == -1) {
            CommonUtils.showToast("Please allow camera permission");
        } else {
            if (requestCode == 1) {
                boolean isgranted = true;
                startScanner();

            }
        }


    }
    @Override
    protected void onResume() {
        try {
            mScannerView.stopCamera();
            mScannerView.startCamera();
        } catch (Exception e) {
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        try {
            mScannerView.stopCamera();
        } catch (Exception e) {
        }
        super.onPause();


    }


}

