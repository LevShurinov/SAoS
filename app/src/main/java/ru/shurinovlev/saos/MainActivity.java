package ru.shurinovlev.saos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    Button start, theory, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        theory = findViewById(R.id.theory);
        about = findViewById(R.id.about);

        theory.setTextColor(Color.GRAY);



        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
        }

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.start:
                        Intent intent_start = new Intent(MainActivity.this, UI.class);
                        startActivity(intent_start);
                        break;
                    case R.id.theory:
                        Toast.makeText(MainActivity.this, "Скоро!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Intent intent_about = new Intent(MainActivity.this, About.class);
                        startActivity(intent_about);
                        break;
                }
            }
        };
        start.setOnClickListener(onclick);
        theory.setOnClickListener(onclick);
        about.setOnClickListener(onclick);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File f = new File(Environment.getExternalStorageDirectory().toString() + "/SAoS/");
                    try{
                        f.mkdir();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    // permission granted
                } else {
                    // permission denied
                }
                return;
        }
    }
}
