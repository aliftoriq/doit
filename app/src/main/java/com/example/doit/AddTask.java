package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTask extends AppCompatActivity implements View.OnClickListener{

    private EditText inputTask, inputDate, inputTime;
    private Button btAddTask;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        getSupportActionBar().hide();

        inputTask = (EditText) findViewById(R.id.input_task);
        inputDate = (EditText) findViewById(R.id.input_date);
        inputTime = (EditText) findViewById(R.id.input_time);

        btAddTask = (Button)  findViewById(R.id.bt_add_task);

        progressDialog = new ProgressDialog(this);

        btAddTask.setOnClickListener(this);

    }

    private void addData(){
        final String taskDesc = inputTask.getText().toString().trim();
        final String date = inputDate.getText().toString().trim();
        final String time = inputTime.getText().toString().trim();

        progressDialog.setMessage("Adding Task...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            progressDialog.setMessage(response+e.toString());
                            progressDialog.show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task", taskDesc);
                params.put("date", date);
                params.put("time", time);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == btAddTask)
            addData();
            startActivity(new Intent(this, Task.class));
    }
}