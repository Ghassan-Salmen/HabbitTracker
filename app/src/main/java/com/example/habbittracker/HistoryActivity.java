package com.example.habbittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.habbittracker.adapter.HabitAdapter;
import com.example.habbittracker.adapter.HistoryAdater;
import com.example.habbittracker.model.HabitModel;
import com.example.habbittracker.model.HistoryModel;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noHistoryTV;
    HistoryAdater adapter;
    Button clearHistoryBtn;
    ArrayList<HistoryModel> arrayList;

    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.all_history_recyclerview);
        noHistoryTV = findViewById(R.id.no_history_text_view);
        clearHistoryBtn = findViewById(R.id.clear_history_btn);

        myDB = new MyDB(this);
        arrayList = myDB.fetchHistory();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clearHistoryBtn.setOnClickListener(view -> {
            myDB.deleteAllHistory();
            arrayList.clear();
            emptyHistory();
            adapter.notifyDataSetChanged();
        });

        adapter = new HistoryAdater(this,arrayList);
        recyclerView.setAdapter(adapter);
        emptyHistory();


    }

    void emptyHistory(){

        if (arrayList == null || arrayList.isEmpty()) {
            noHistoryTV.setVisibility(View.VISIBLE);
        } else {
            noHistoryTV.setVisibility(View.GONE); // or View.INVISIBLE if you prefer
        }
    }
}