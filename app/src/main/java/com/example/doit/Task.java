package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Task extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttonDashboard;
    private ImageButton buttonUser;
    Button buttonAddTask;
    RecyclerView taskRecycleView;
    private ToDoAdapter taskAdapter;

    private List<ToDoModel> taskList;

    @SuppressLint({"WrongViewCast", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        getSupportActionBar().hide();

        taskRecycleView = findViewById(R.id.recycleview_task);
        taskRecycleView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        buttonDashboard= (ImageButton) findViewById(R.id.buttonDashboard);
        buttonUser = (ImageButton) findViewById(R.id.buttonUser);
        buttonAddTask = (Button) findViewById(R.id.bt_add_task);

        buttonDashboard.setOnClickListener(this);
        buttonUser.setOnClickListener(this);
        buttonAddTask.setOnClickListener(this);

    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    taskList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        ToDoModel task = new ToDoModel();
                        task.setTask(data.getString("task"));
                        task.setStatus(data.getInt("status"));
                        task.setDate(data.getString("date"));
                        task.setTime(data.getString("time"));
                        task.setId(data.getInt("id"));
                        taskList.add(task);
                    }
                    taskAdapter = new ToDoAdapter(Task.this);
                    taskRecycleView.setAdapter(taskAdapter);
                    Collections.reverse(taskList);
                    taskAdapter.setTask(taskList);
                    taskAdapter.setOnItemClickListener(new ToDoAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            taskAdapter.deleteItem(position);
                        }
                    });
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
    }

    @Override
    public void onClick(View view) {
        if (view == buttonDashboard)
            startActivity(new Intent(this, Dasboard.class));
        if (view == buttonUser)
            startActivity(new Intent(this, User.class));
        if (view == buttonAddTask)
            startActivity(new Intent(this, AddTask.class));
    }
}