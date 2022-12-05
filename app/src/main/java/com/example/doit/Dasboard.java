package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doit.Adapter.ToDoAdapter;
import com.example.doit.Model.ToDoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dasboard extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttonTask;
    private ImageButton buttonUser;
    private Button buttonTask1;
    private List<ToDoModel> taskList;

    private TextView t1,t2,d1,d2;
    private boolean cek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        taskList = new ArrayList<>();

        getSupportActionBar().hide();

        buttonTask = (ImageButton) findViewById(R.id.buttonTask);
        buttonUser = (ImageButton) findViewById(R.id.buttonUser);

        buttonTask1 = (Button) findViewById(R.id.buttonMyTask);

        t1 = (TextView) findViewById(R.id.task_text1);
        t2 = (TextView) findViewById(R.id.task_text2);
        d1 = (TextView) findViewById(R.id.task_date1);
        d2 = (TextView) findViewById(R.id.task_date2);

        taskList = getData();

        buttonTask.setOnClickListener(this);
        buttonUser.setOnClickListener(this);
        buttonTask1.setOnClickListener(this);

    }

    public void setCek(boolean c){
        this.cek = c;
    }

    private List<ToDoModel> getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    try {
                        taskList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            ToDoModel task = new ToDoModel();
                            if(data.getInt("status")==0){
                                task.setTask(data.getString("task"));
                                task.setStatus(data.getInt("status"));
                                task.setDate(data.getString("date"));
                                task.setTime(data.getString("time"));
                                task.setId(data.getInt("id"));
                                taskList.add(task);
                            }

                        }
                        t1.setText(taskList.get(1).getTask());
                        d1.setText(taskList.get(1).getDate());
                        if(jsonArray.getJSONObject(0).length() > 1){
                            t2.setText(taskList.get(2).getTask());
                            d2.setText(taskList.get(2).getDate());
                        }

                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(), "no task to do", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Failed to get data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to get data", Toast.LENGTH_LONG).show();
            }
        });


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



        return taskList;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonTask)
            startActivity(new Intent(this, Task.class));
        if (view == buttonTask1)
            startActivity(new Intent(this, Task.class));
        if (view == buttonUser)
            startActivity(new Intent(this, User.class));
    }

}