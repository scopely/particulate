package com.scopely.particulate.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scopely.particulate.example.activities.GravityWellActivity;
import com.scopely.particulate.example.activities.PathActivity;
import com.scopely.particulate.example.activities.PointActivity;
import com.scopely.particulate.example.activities.SnowActivity;
import com.scopely.particulate.example.activities.TouchPointActivity;
import com.scopely.particulate.example.activities.ViewBoundActivity;
import com.scopely.particulate.example.activities.ViewCenterActivity;

public class MainActivity extends android.app.Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(0);

        setContentView(R.layout.activity_main);

        findViewById(R.id.point_emitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PointActivity.class));
            }
        });
        findViewById(R.id.path_emitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PathActivity.class));
            }
        });
        findViewById(R.id.gravity_well).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GravityWellActivity.class));
            }
        });
        findViewById(R.id.touch_point_emitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchPointActivity.class));
            }
        });
        findViewById(R.id.snow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SnowActivity.class));
            }
        });
        findViewById(R.id.bound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewBoundActivity.class));
            }
        });
        findViewById(R.id.center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewCenterActivity.class));
            }
        });
    }
}
