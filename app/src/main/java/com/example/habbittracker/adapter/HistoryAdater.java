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
import com.example.habbittracker.model.HistoryModel;

import java.util.ArrayList;

public class HistoryAdater extends RecyclerView.Adapter<HistoryAdater.ItemViewHolder> {


    Context context;
    ArrayList<HistoryModel> historyModels;

    public HistoryAdater(Context context, ArrayList<HistoryModel> historyModels) {
        this.context = context;
        this.historyModels = historyModels;

    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_view_row,parent,false);
        HistoryAdater.ItemViewHolder holder = new HistoryAdater.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        HistoryModel historyModel = historyModels.get(position);
        holder.habit.setText(historyModel.getHabit());
        holder.time.setText(historyModel.getTime());
        holder.date.setText(historyModel.getDate());
        holder.score.setText(String.valueOf(historyModel.getScore()));
        holder.duration.setText(String.valueOf(historyModel.getDuration()) + " minutes");
        if(historyModel.getIncrease()==1){
            holder.increase.setText("+");
            holder.score.setTextColor(Color.GREEN);
            holder.increase.setTextColor(Color.GREEN);
        }
        else {
            holder.increase.setText("-");
            holder.score.setTextColor(Color.RED);
            holder.increase.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView habit,score,duration,increase,time,date;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            habit = itemView.findViewById(R.id.history_habit_name_recycler_item);
            score = itemView.findViewById(R.id.history_habit_score_recycler_item);
            duration = itemView.findViewById(R.id.history_habit_duration_recycler_item);
            time = itemView.findViewById(R.id.history_habit_time_recycler_item);
            date = itemView.findViewById(R.id.history_habit_date_recycler_item);
            increase = itemView.findViewById(R.id.history_habit_score_increase_recycler_item);
        }
    }
}
