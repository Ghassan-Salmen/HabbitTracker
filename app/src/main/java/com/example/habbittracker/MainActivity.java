package com.example.habbittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.habbittracker.adapter.HabitAdapter;
import com.example.habbittracker.interfaces.SelectListener;
import com.example.habbittracker.model.HabitModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    TextView noHabitsTV,scoreTV;
    MyDB myDB;
    HabitAdapter adapter;
    FloatingActionButton fab;
    ArrayList<HabitModel> arrayList;
    HabitModel habitModel;
    SharedPreferences sharedPreferences ;
    int totalScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        totalScore = sharedPreferences.getInt("score", 0);


        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.all_habits_recyclerview);
        noHabitsTV = findViewById(R.id.no_habits_text_view);
        scoreTV = findViewById(R.id.total_score_no);
        scoreTV.setText(String.valueOf(totalScore));
        myDB = new MyDB(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(intent);

            }
        });

        arrayList = myDB.fetchHabits();
        for(HabitModel arr : arrayList){
            Log.e("array",arr.getHabit());
        }



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HabitAdapter(this,arrayList,this);
        recyclerView.setAdapter(adapter);

        emptyHabits();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    habitModel = arrayList.get(position);
                    arrayList.remove(position);
                    myDB.deleteHabit(habitModel.getId());
                    emptyHabits();
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView,habitModel.getHabit()+" is deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    arrayList.add(position,habitModel);
                                    emptyHabits();
                                    myDB.addHabitWithHabitModel(habitModel);
                                    adapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;

            }


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red_delete))
                    .addSwipeLeftActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history_item:

                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                return true;
                case R.id.clear_score_item:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert!!")
                        .setMessage("Do you really  want to clear your score?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                totalScore = 0;
                                editor.putInt("score", totalScore);
                                editor.apply();
                                scoreTV.setText(String.valueOf(sharedPreferences.getInt("score", 0)));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        }).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemClicked(HabitModel habitModel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert!!")
                .setMessage("select an option?")
                .setCancelable(true)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        Intent intent  = new Intent(MainActivity.this, AddHabitActivity.class);
                        intent.putExtra("update", "update");
                        intent.putExtra("habitId", habitModel.getId());
                        startActivity(intent);



                    }
                })
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (habitModel.getIncrease()){
                            case 1:
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Log.e("score","score = "+totalScore+" getScore = "+habitModel.getScore()+"increase= "+habitModel.getIncrease());
                                totalScore = totalScore + habitModel.getScore();
                                editor.putInt("score", totalScore);
                                editor.apply();
                                break;
                            case 0:
                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                Log.e("score","score = "+totalScore+" getScore = "+habitModel.getScore()+"increase= "+habitModel.getIncrease());
                                totalScore = totalScore - habitModel.getScore();
                                editor1.putInt("score", totalScore);
                                editor1.apply();
                                break;
                        }





                        scoreTV.setText(String.valueOf(sharedPreferences.getInt("score", 0)));
                        myDB.addHistory(habitModel,AndroidUtil.getCurrentTime(),AndroidUtil.getCurrentDate());

                    }
                }).show();

    }

    void emptyHabits(){

        if (arrayList == null || arrayList.isEmpty()) {
            noHabitsTV.setVisibility(View.VISIBLE);
        } else {
            noHabitsTV.setVisibility(View.GONE); // or View.INVISIBLE if you prefer
        }
    }
}

