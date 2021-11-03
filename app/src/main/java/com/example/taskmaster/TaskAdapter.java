package com.example.taskmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.motion.CustomFloatAttributes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter <TaskAdapter.TaskViewHolder>{
    ArrayList<Task> allTasksData = new ArrayList<>();
    private OnTaskItemClickListener listener;

    public interface OnTaskItemClickListener {
        void onItemClicked(int position);
    }

    public TaskAdapter(ArrayList<Task> allTasksData, OnTaskItemClickListener listener) {
        this.allTasksData = allTasksData;
        this.listener = listener;

    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public Task task;
        public TextView title;
        public TextView body;
        public TextView state ;
        View itemView;

         TaskViewHolder(@NonNull View itemView ,OnTaskItemClickListener listener) {
            super(itemView);
            this.itemView =itemView;
            title = itemView.findViewById(R.id.fragment_title);
            body = itemView.findViewById(R.id.fragment_body);
            state = itemView.findViewById(R.id.fragment_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(getAdapterPosition());

                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        return new TaskViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        Task item = allTasksData.get(position);
        taskViewHolder.title.setText(item.title);
        taskViewHolder.body.setText(item.body);
        taskViewHolder.state.setText(item.state);
    }


    @Override
    public int getItemCount() {
        return allTasksData.size();
    }
}
