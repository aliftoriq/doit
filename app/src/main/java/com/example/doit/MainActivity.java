package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button buttonDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

//        buttonDashboard = (Button) findViewById(R.id.startmenu);

//        buttonDashboard.setOnClickListener(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Dasboard.class));
                finish();
            }
        }, 2000L);
    }

    @Override
    public void onClick(View view) {
//        if (view == buttonDashboard)
//            startActivity(new Intent(this, Dasboard.class));
    }
}