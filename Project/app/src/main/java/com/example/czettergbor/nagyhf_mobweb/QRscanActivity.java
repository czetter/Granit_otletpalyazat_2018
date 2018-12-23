package com.example.czettergbor.nagyhf_mobweb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRscanActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView txtQr;
    BarcodeDetector barcodeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);


        surfaceView = findViewById(R.id.cameraprew);
        txtQr = findViewById(R.id.txtQr);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRscanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(QRscanActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        return;
                    }
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    Log.d("QR", e.toString());
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    txtQr.post(new Runnable() {
                        @Override
                        public void run() {
                            String scanned = qrCodes.valueAt(0).displayValue;
                            String[] splitScanned = scanned.split("\\.");
                            if (splitScanned[0].equals("account")) {
                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(100);
                                cameraSource.stop();
                                Intent mIntent = new Intent(QRscanActivity.this, MoneyTransferActivity.class);
                                mIntent.putExtra("QrScanned", true);
                                mIntent.putExtra("QrName", splitScanned[1]);
                                mIntent.putExtra("QrAccNum", splitScanned[2]);
                                txtQr.setText(qrCodes.valueAt(0).displayValue);
                                startActivity(mIntent);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recreate();
    }
}
