package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class User extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttonDashboard;
    private ImageButton buttonTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().hide();

        buttonDashboard = (ImageButton) findViewById(R.id.buttonDashboard);
        buttonTask = (ImageButton) findViewById(R.id.buttonTask);

        buttonDashboard.setOnClickListener(this);
        buttonTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonTask)
            startActivity(new Intent(this, Task.class));
        if (view == buttonDashboard)
            startActivity(new Intent(this, Dasboard.class));
    }

}