package com.example.habbittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.habbittracker.model.HabitModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddHabitActivity extends AppCompatActivity {

    EditText habitTV,scoreTV,durationTV;
    Switch increaseSwitch;
    Button addHabitBtn;
    MyDB myDB;
    int habitId;
    String updateValue;
    HabitModel habitModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);



        habitTV = (EditText) findViewById(R.id.habit_et);
        scoreTV = (EditText) findViewById(R.id.habit_score_et);
        durationTV = (EditText) findViewById(R.id.habit_duration_et);
        increaseSwitch = (Switch)  findViewById(R.id.increase_switch);
        addHabitBtn = (Button)  findViewById(R.id.add_habit_btn);

        myDB = new MyDB(this);
        addHabitBtn.setOnClickListener(v->{
            addHabit();
        });



        Intent intent = getIntent();


        updateValue = intent.getStringExtra("update");

        habitId = intent.getIntExtra("habitId", -1);


        if (updateValue != null && updateValue.equals("update")) {

            if(habitId != -1){
                retrieveHabitInfo();
                addHabitBtn.setText("update Habit");
            }
        }





        increaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    increaseSwitch.setText("increase");
                }else{
                    increaseSwitch.setText("decrease");
                }
            }
        });


    }

    private void retrieveHabitInfo() {

         habitModel = myDB.fetchHabitById(habitId);

        habitTV.setText(habitModel.getHabit());
        scoreTV.setText(String.valueOf(habitModel.getScore()));
        durationTV.setText(String.valueOf(habitModel.getDuration()));

        switch (habitModel.getIncrease()){
            case 1:
                increaseSwitch.setChecked(true);
                break;
            case 0:
                increaseSwitch.setChecked(false);
                break;
        }



    }

    private void addHabit() {

        String habit= habitTV.getText().toString();
        String scoreString = scoreTV.getText().toString();
        String durationString = durationTV.getText().toString();


        int increase=0;
        if(increaseSwitch.isChecked())
            increase = 1;
        if(habit.isEmpty()){

            habitTV.setError("empty Field");
            return;
        }if(scoreString.isEmpty()){


            scoreTV.setError("empty Field");
            return;
        }if(durationString.isEmpty()){

            durationTV.setError("empty Field");
            return;
        }
        int score= Integer.parseInt(scoreString);
        int duration= Integer.parseInt(durationString);



        boolean result;
        if (updateValue != null && updateValue.equals("update")) {

            if(habitId != -1){
                habitModel = new HabitModel(increase,score,habit,duration);
                Log.e("habit",habitModel.getHabit()+"id = "+habitId);
                result = myDB.updateHabit(habitModel,habitId);
               ArrayList<HabitModel> arrayList = myDB.fetchHabits();
                for(HabitModel arr : arrayList){
                    Log.e("array",arr.getHabit());
                }
                if(result){
                    Toast.makeText(this, "habit is updated successfully", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }else{
            result = myDB.addHabit(habit,score,duration,increase);
            if(result){
                Toast.makeText(this, "habit is added successfully", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }



        Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
        //user can't go back to this activity by clicking back button
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}