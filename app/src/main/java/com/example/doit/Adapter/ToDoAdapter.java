package com.example.doit.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doit.Constants;
import com.example.doit.Model.ToDoModel;
import com.example.doit.R;
import com.example.doit.RequestHandler;
import com.example.doit.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todolist;
    private Task activity;
    private ProgressDialog progressDialog;
    private OnItemClickListener listener;

    public ToDoAdapter(Task activity){
        this.activity = activity;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView,listener);
    }

    public void deleteItem(int position){
        ToDoModel item = todolist.get(position);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                int id =  item.getId();
                params.put("id", "\""+ id + "\"");
                return params;
            }
        };
        RequestHandler.getInstance(activity).addToRequestQueue(stringRequest);

        todolist.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        ToDoModel item = todolist.get(position);
        holder.task.setText(item.getTask());
        holder.cb.setChecked(toBoolean(item.getStatus()));
        holder.time.setText(item.getTime());
        holder.date.setText(item.getDate());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_UPDATE_STATUS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        if(b){
                            params.put("status", "1");
                        }else{
                            params.put("status", "0");
                        }

                        int id =  item.getId();

                        params.put("id", "\""+ id + "\"");
                        return params;
                    }
                };

                RequestHandler.getInstance(activity).addToRequestQueue(stringRequest);
            }
        });
    }

    private boolean toBoolean(int status) {
        return status!=0;
    }

    @Override
    public int getItemCount() {
        return todolist.size() ;
    }

    public  void setTask(List<ToDoModel> todolist){
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        TextView task, time, date;
        ImageView del;

        ViewHolder(View view, OnItemClickListener listener){
            super(view);
            task = view.findViewById(R.id.task_text);
            cb = view.findViewById(R.id.task_cb);
            time = view.findViewById(R.id.task_time);
            date = view.findViewById(R.id.task_date);
            del = view.findViewById(R.id.bt_delete);

            del.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
