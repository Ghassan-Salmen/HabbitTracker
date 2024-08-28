package com.example.habbittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.habbittracker.model.HabitModel;
import com.example.habbittracker.model.HistoryModel;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="HabitsDB";
    private static final int DATABASE_VERSION = 1;
    //habit table
    private static final String TABLE_HABIT = "habit";
    private static final String KEY_ID = "id";
    private static final String KEY_HABIT = "habit";
    private static final String KEY_SCORE = "score";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_INCREASE = "increase";
    //history table
    private static final String TABLE_HISTORY = "history";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_HABIT+
                "("+KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"+KEY_HABIT +" TEXT,"+ KEY_DURATION+" INTEGER,"+KEY_SCORE +" INTEGER,"+
                KEY_INCREASE+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_HISTORY+
                "("+KEY_ID+" INTEGER,"+KEY_TIME+" TEXT,"+KEY_DATE+" TEXT,"+KEY_HABIT +" TEXT,"+ KEY_DURATION+" INTEGER,"+KEY_SCORE +" INTEGER,"+
                KEY_INCREASE+" INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_HABIT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORY);
        onCreate(sqLiteDatabase);

    }

    public boolean addHabitWithHabitModel(HabitModel habitModel){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,habitModel.getId());
        values.put(KEY_HABIT,habitModel.getHabit());
        values.put(KEY_SCORE,habitModel.getScore());
        values.put(KEY_DURATION,habitModel.getDuration());
        values.put(KEY_INCREASE,habitModel.getIncrease());

        long result = db.insert(TABLE_HABIT,null,values);
        db.close();
        return result != -1;

    }

    public boolean addHabit(String habit,int score,int duration,int increase){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HABIT,habit);
        values.put(KEY_SCORE,score);
        values.put(KEY_DURATION,duration);
        values.put(KEY_INCREASE,increase);

        long result = db.insert(TABLE_HABIT,null,values);
        db.close();
        return result != -1;

    }

    public boolean addHistory(HabitModel habitModel,String time,String date){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,habitModel.getId());
        values.put(KEY_TIME,time);
        values.put(KEY_DATE,date);
        values.put(KEY_HABIT,habitModel.getHabit());
        values.put(KEY_DURATION,habitModel.getDuration());
        values.put(KEY_SCORE,habitModel.getScore());
        values.put(KEY_INCREASE,habitModel.getIncrease());


        long result = db.insert(TABLE_HISTORY,null,values);
        db.close();

        return result != -1;

    }

    public ArrayList<HistoryModel> fetchHistory(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_HISTORY,null);

        ArrayList<HistoryModel> arrHistory = new ArrayList<>();
        while(cursor.moveToNext()){

            HistoryModel model = new HistoryModel();
            model.setId(cursor.getInt(0));
            model.setTime(cursor.getString(1));
            model.setDate(cursor.getString(2));
            model.setHabit(cursor.getString(3));
            model.setDuration(cursor.getInt(4));
            model.setScore(cursor.getInt(5));
            model.setIncrease(cursor.getInt(6));

            arrHistory.add(model);
        }

        cursor.close();

        return arrHistory;
    }

    public ArrayList<HabitModel> fetchHabits(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_HABIT,null);

        ArrayList<HabitModel> arrHabits = new ArrayList<>();
        while(cursor.moveToNext()){

            HabitModel model = new HabitModel();
            model.setId(cursor.getInt(0));
            model.setHabit(cursor.getString(1));
            model.setDuration(cursor.getInt(2));
            model.setScore(cursor.getInt(3));
            model.setIncrease(cursor.getInt(4));

            arrHabits.add(model);
        }

        cursor.close();

        return arrHabits;
    }

    public boolean updateHabit(HabitModel habitModel,int id){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_HABIT,habitModel.getHabit());
        values.put(KEY_SCORE,habitModel.getScore());
        values.put(KEY_DURATION,habitModel.getDuration());
        values.put(KEY_INCREASE,habitModel.getIncrease());

        long result = database.update(TABLE_HABIT,values,KEY_ID+" = "+id,null);



        return result != -1;
    }

    public HabitModel fetchHabitById(int habitId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                KEY_ID,
                KEY_HABIT,
                KEY_DURATION,
                KEY_SCORE,
                KEY_INCREASE
        };

        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(habitId)};

        Cursor cursor = db.query(
                TABLE_HABIT,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        HabitModel habitModel = null;

        if (cursor != null && cursor.moveToFirst()) {
            habitModel = new HabitModel();
            habitModel.setId(cursor.getInt(0));
            habitModel.setHabit(cursor.getString(1));
            habitModel.setDuration(cursor.getInt(2));
            habitModel.setScore(cursor.getInt(3));
            habitModel.setIncrease(cursor.getInt(4));

            cursor.close();
        }

        return habitModel;
    }

    public boolean deleteAllHistory() {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_HISTORY, null, null);

        db.close();

        return result != -1;
    }


    public boolean deleteHabit(int id){


        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_HABIT,KEY_ID+" = ?",new String[]{String.valueOf(id)});

        db.close();

        return result != -1;
    }

    public boolean deleteHistory(String time,String date){


        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "time = ? AND date = ?";
        String[] whereArgs = new String[]{time, date}; // Replace with your actual time and date values


        long result = db.delete(TABLE_HABIT,whereClause,whereArgs);

        db.close();

        return result != -1;
    }
}
