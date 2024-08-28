package com.example.habbittracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habbittracker.R;
import com.example.habbittracker.interfaces.SelectListener;
import com.example.habbittracker.model.HabitModel;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ItemViewHolder> {


    Context context;
    ArrayList<HabitModel> habitModels;
    private final SelectListener listener;


    public HabitAdapter(Context context, ArrayList<HabitModel> habitModels,SelectListener listener) {
        this.context = context;
        this.habitModels = habitModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_recycler_view_row,parent,false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        HabitModel habitModel = habitModels.get(position);
        holder.habit.setText(habitModel.getHabit());
        holder.score.setText(String.valueOf(habitModel.getScore()));
        holder.duration.setText(String.valueOf(habitModel.getDuration()) + " minutes");
        if(habitModel.getIncrease()==1){
            holder.increase.setText("+");
        holder.score.setTextColor(Color.GREEN);
        holder.increase.setTextColor(Color.GREEN);
        }
        else {
            holder.increase.setText("-");
            holder.score.setTextColor(Color.RED);
            holder.increase.setTextColor(Color.RED);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(habitModel);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return habitModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView habit,score,duration,increase;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            habit = itemView.findViewById(R.id.habit_name_recycler_item);
            score = itemView.findViewById(R.id.habit_score_recycler_item);
            duration = itemView.findViewById(R.id.habit_duration_recycler_item);
            increase = itemView.findViewById(R.id.habit_score_increase_recycler_item);
        }
    }

}
