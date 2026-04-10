package com.example.apkpiw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgFoto;
    EditText etIdKaryawan;
    Button btnAmbilFoto, btnVerifikasi;
    Bitmap fotoBitmap = null;

    private static final int REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgFoto       = findViewById(R.id.imgFoto);
        etIdKaryawan  = findViewById(R.id.etIdKaryawan);
        btnAmbilFoto  = findViewById(R.id.btnAmbilFoto);
        btnVerifikasi = findViewById(R.id.btnVerifikasi);

        // Implicit Intent — buka kamera bawaan HP
        btnAmbilFoto.setOnClickListener(v -> {
            Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(kameraIntent, REQUEST_CAMERA);
        });

        // Explicit Intent — pindah ke DetailActivity
        btnVerifikasi.setOnClickListener(v -> {
            String idKaryawan = etIdKaryawan.getText().toString().trim();

            if (idKaryawan.isEmpty()) {
                etIdKaryawan.setError("ID Karyawan tidak boleh kosong!");
                return;
            }

            if (fotoBitmap == null) {
                Toast.makeText(this, "Harap ambil foto selfie dulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("ID_KARYAWAN", idKaryawan);
            intent.putExtra("FOTO", fotoBitmap);
            startActivity(intent);
        });
    }

    // Menangkap hasil foto dari kamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            fotoBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(fotoBitmap);
        }
    }
}