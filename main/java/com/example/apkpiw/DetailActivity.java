package com.example.apkpiw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView imgHasil;
    TextView tvIdHasil;
    Button btnLokasi, btnSelesai;

    // Koordinat Gedung Sate Bandung
    private static final double LAT_KANTOR = -6.9022;
    private static final double LNG_KANTOR = 107.6188;
    private static final String NAMA_KANTOR = "Gedung+Sate+Bandung";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgHasil   = findViewById(R.id.imgHasil);
        tvIdHasil  = findViewById(R.id.tvIdHasil);
        btnLokasi  = findViewById(R.id.btnLokasi);
        btnSelesai = findViewById(R.id.btnSelesai);

        // Terima data dari MainActivity
        String idKaryawan = getIntent().getStringExtra("ID_KARYAWAN");
        Bitmap foto = (Bitmap) getIntent().getParcelableExtra("FOTO");

        tvIdHasil.setText(idKaryawan);
        if (foto != null) {
            imgHasil.setImageBitmap(foto);
        }

        // Implicit Intent — langsung lock ke Gedung Sate Bandung
        btnLokasi.setOnClickListener(v -> {
            Uri lokasi = Uri.parse(
                    "geo:" + LAT_KANTOR + "," + LNG_KANTOR +
                            "?q=" + LAT_KANTOR + "," + LNG_KANTOR +
                            "(" + NAMA_KANTOR + ")&z=18"
            );
            Intent mapsIntent = new Intent(Intent.ACTION_VIEW, lokasi);
            mapsIntent.setPackage("com.google.android.apps.maps");

            if (mapsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapsIntent);
            } else {
                // Fallback ke browser jika Maps tidak ada
                Uri webLokasi = Uri.parse(
                        "https://www.google.com/maps?q=" + LAT_KANTOR + "," + LNG_KANTOR
                );
                startActivity(new Intent(Intent.ACTION_VIEW, webLokasi));
            }
        });

        // Selesai — clear semua history dan kembali ke MainActivity
        btnSelesai.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}