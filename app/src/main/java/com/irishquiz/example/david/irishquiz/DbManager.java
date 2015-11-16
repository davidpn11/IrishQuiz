package com.irishquiz.example.david.irishquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by David on 11/14/15.
 */
public class DbManager extends SQLiteOpenHelper{

    private static final String db_name = "IrishQuiz.db";
    private static final String table_player = "player";
    private static final String player_login = "PLAYER_LOGIN";
    private static final String player_passwd = "PLAYER_PASSWD";
    private static final String player_score = "PLAYER_SCORE";
    private static final String table_question = "questions";
    private static final String question_id = "QUESTION_ID";
    private static final String question_sentence = "QUESTION_SENTENCE";
    private static final String question_op1 = "QUESTION_OP1";
    private static final String question_op2 = "QUESTION_OP2";
    private static final String question_op3 = "QUESTION_OP3";
    private static final String question_op4 = "QUESTION_OP4";
    private static final String question_answer = "QUESTION_ANSWER";


    DbManager(Context context)
    {
        super(context, db_name, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DATABASE", "Constructor");
    }

    @Override

    public void onCreate(SQLiteDatabase db)
    {
        Log.d("DATABASE", "onCreate Called");

        db.execSQL("create table " + table_player + "" +
                "(PLAYER_LOGIN TEXT," +
                " PLAYER_PASSWD TEXT," +
                " PLAYER_SCORE INTEGER);");

        db.execSQL("create table " + table_question + "" +
                "( QUESTION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " QUESTION_SENTENCE TEXT," +
                " QUESTION_OP1 TEXT," +
                " QUESTION_OP2 TEXT," +
                " QUESTION_OP3 TEXT," +
                " QUESTION_OP4 TEXT," +
                " QUESTION_ANSWER INTEGER);");
    }

    @Override
    //
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + db_name);
        onCreate(db);
    }

    public boolean onInsert_PLAYER(String login,String passwd){



        Log.d("DATABASE","onInsertPLAYER Called");
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        c = db.rawQuery("SELECT * FROM " + table_player+" where player_login = \""+login+"\";", null);



        Log.e("INSERT_PLAYER:", c.toString());
        if(c.getCount() ==0) {
            ContentValues values = new ContentValues();
            values.put(player_login, login);
            values.put(player_passwd, passwd);
            values.put(player_score, 0);

            long check;
            check = db.insert(table_player, null, values);

            if (check == -1) {
                return false;
            } else
                return true;
        }
        else{
            return false;
        }
    }

    public boolean login(String login,String passwd){

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        c = db.rawQuery("SELECT * FROM " + table_player+" where player_login = \""+login+"\" " +
                "and player_passwd = \""+passwd+"\" ;", null);
        if(c.getCount() ==0){
            return false;
        }
        else{
            return true;
        }

    }

    public String getScore(String login){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        c = db.rawQuery("SELECT player_score FROM " + table_player+" where player_login = \""+login+"\";", null);

        StringBuffer buf = new StringBuffer();

        if(c.getCount() == 0 || c.getCount() > 1){
            return null;
        }
        else{
             while(c.moveToNext()){
                buf.append(c.getString(0));
             }
            return buf.toString();
        }


    }

    public boolean onInsert_QUESTION(String sentence,String op1,String op2,String op3,
                                     String op4,int answer){

        Log.d("DATABASE","onInsert Called");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(question_sentence,sentence);
        values.put(question_op1,op1);
        values.put(question_op2, op2);
        values.put(question_op3,op3);
        values.put(question_op4, op4);
        values.put(question_answer, answer);

        long check;
        check = db.insert(table_question,null,values);

        if(check == -1){
            return false;
        }
        else
            return true;
    }

    @Override
    public synchronized void close() {
        Log.e("DATABASE", "DB Closed");
        super.close();
    }


    public JSONArray getAllQuestions(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        c = db.rawQuery("SELECT * FROM " + table_question+";", null);

        JSONArray array = new JSONArray();

        if(c.getCount() ==0){
            return null;
        }else{
            Log.e("N of questions", ""+c.getCount());
            try{
            while (c.moveToNext()){
                JSONObject obj = new JSONObject();
                obj.put("id",c.getString(0));
                obj.put("sentence",c.getString(1));
                obj.put("op1",c.getString(2));
                obj.put("op2",c.getString(3));
                obj.put("op3",c.getString(4));
                obj.put("op4",c.getString(5));
                obj.put("answer",c.getString(6));
                array.put(obj);
            }

                return array;
            }catch(JSONException e) {
                Log.e("JSON Exception","");
                e.printStackTrace();
                return null;
            }

        }


    }

    public ArrayList getAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM player ORDER BY player_score DESC", null);


        ArrayList<String> array = new ArrayList<>();

        if(c.getCount() ==0){
            return null;
        }else{
            Log.e("N of player", ""+c.getCount());

                String temp;
                while (c.moveToNext()){
                    temp = c.getString(0)+"-"+c.getString(2);
                    Log.e("player",temp);
                    array.add(temp);
                }

                return array;
        }

    }


    public boolean setScore(String login,int new_score){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT player_score FROM " + table_player + " where player_login = \"" + login + "\";", null);

        int score =0;
        if(c.getCount()==1) {
            while (c.moveToNext()) {
                score = c.getInt(0);
            }


            if (new_score > score) {
                db.execSQL("UPDATE PLAYER SET player_score = "+new_score+" WHERE player_login = \"" + login + "\";");
                return true;
            }else{
                return false;
            }

        }else{
            return false;
        }
    }


}
