package com.irishquiz.example.david.irishquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InterruptedIOException;

public class GameActivity extends AppCompatActivity {

    TextView sentence;
    ImageView mark;
    Button op1,op2,op3,op4;
    ImageButton jumpBtn;
    private MediaPlayer success,fail,jump;
    int Score = 0,array_lenght = 0,n_question = 0,jump_count = 3;
    CountDownTimer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Irish Quiz");

        final TextView counter = (TextView)findViewById(R.id.counter);
        final DbManager db = new DbManager(this);

        mark = (ImageView) findViewById(R.id.mark);
        sentence = (TextView) findViewById(R.id.question);
        op1 = (Button) findViewById(R.id.op1);
        op2 = (Button) findViewById(R.id.op2);
        op3 = (Button) findViewById(R.id.op3);
        op4 = (Button) findViewById(R.id.op4);
        jumpBtn = (ImageButton) findViewById(R.id.jumpBtn);
        jumpBtn.setVisibility(View.VISIBLE);
        jumpBtn.setClickable(true);
        fail = MediaPlayer.create(getApplicationContext(),R.raw.failure);
        fail.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                fail.release();
                fail = MediaPlayer.create(getApplicationContext(),R.raw.failure);
                fail.setOnCompletionListener(this);
            }
        });
         success = MediaPlayer.create(getApplicationContext(),R.raw.success);
        success.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                success.release();
                success = MediaPlayer.create(getApplicationContext(),R.raw.success);
                success.setOnCompletionListener(this);
            }
        });
        jump = MediaPlayer.create(getApplicationContext(),R.raw.jumping);
        jump.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                jump.release();
                jump = MediaPlayer.create(getApplicationContext(),R.raw.jumping);
                jump.setOnCompletionListener(this);
            }
        });



        //jumpBtn.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        JSONArray array = db.getAllQuestions();
        array_lenght = array.length();
       // try{
        Log.e("David",getIntent().getStringExtra("player"));
            startGame(array);
        /*}catch (JSONException e){
            e.printStackTrace();
        }*/



        //JSONObject obj = new JSONObject();
        try{
            setQuestion(array.getJSONObject(0));

        }catch(JSONException e){
            e.printStackTrace();
        }


        count = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                counter.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                counter.setText("0");
                Intent it = new Intent(GameActivity.this,ResultActivity.class);
                it.putExtra("score",Score);

                it.putExtra("player",getIntent().getStringExtra("player"));
                startActivity(it);
                finish();
            }
        };
        count.start();
    }



    public void startGame(final JSONArray jsonArray){

        try{

                final JSONObject obj = jsonArray.getJSONObject(n_question);

                final int answer = obj.getInt("answer");
                setQuestion(obj);


                op1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = getAnswer(answer, 1);


                        if (b) {
                            success.start();
                            setMark(true);
                        } else {
                            fail.start();
                            setMark(false);
                        }

                        n_question++;
                        if(n_question < array_lenght){

                            startGame(jsonArray);
                        }
                        else{
                            count.onFinish();
                            Toast.makeText(GameActivity.this, "OUT OF QUESTIONS", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                op2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = getAnswer(answer, 2);
                       // player.reset();
                        if (b) {
                            success.start();
                            setMark(true);
                        } else {
                            fail.start();
                            setMark(false);
                        }
                        n_question++;
                        if (n_question < array_lenght)
                            startGame(jsonArray);
                        else {
                            count.onFinish();
                            Toast.makeText(GameActivity.this, "OUT OF QUESTIONS", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                op3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b =  getAnswer(answer,3);
                       // player.reset();
                        if (b) {
                            success.start();
                            setMark(true);
                        } else {
                            fail.start();
                            setMark(false);
                        }
                        n_question++;
                        if(n_question < array_lenght)
                            startGame(jsonArray);
                        else{
                            count.onFinish();
                            Toast.makeText(GameActivity.this, "OUT OF QUESTIONS", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                op4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = getAnswer(answer,4);
                       // player.reset();
                        if (b) {
                            success.start();
                            setMark(true);
                        } else {
                            fail.start();
                            setMark(false);
                        }
                        n_question++;
                        if(n_question < array_lenght)
                            startGame(jsonArray);
                        else{
                            count.onFinish();
                            Toast.makeText(GameActivity.this, "OUT OF QUESTIONS", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                jumpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(jump_count > 0){
                            jump.start();
                            n_question++;
                            jump_count--;
                            Toast.makeText(GameActivity.this, jump_count+" jumps remaining!", Toast.LENGTH_SHORT).show();

                            if(jump_count == 0){
                                jumpBtn.setVisibility(View.INVISIBLE);
                                jumpBtn.setClickable(false);
                            }

                            if(n_question < array_lenght){

                                startGame(jsonArray);
                            }
                            else{
                                count.onFinish();
                                Toast.makeText(GameActivity.this, "OUT OF QUESTIONS", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            jumpBtn.setClickable(false);
                            Toast.makeText(GameActivity.this, "NO MORE JUMPS", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void setMark(boolean b){


        Log.e("Tick", "called");

        if(b)
            mark.setImageResource(R.drawable.tick);
        else{
            mark.setImageResource(R.drawable.error);
        }

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mark.setImageResource(0);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    public boolean getAnswer(int ans,int op){

        if(ans == op){
            Score++;
            return true;
        }else{
            Score--;
            return false;
        }

    }

    public void setQuestion(JSONObject j){

      /*  try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }*/

        try{
            op1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            op2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            op3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            op4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

            sentence.setText(j.getString("sentence"));
            op1.setText(j.getString("op1"));
            op2.setText(j.getString("op2"));
            op3.setText(j.getString("op3"));
            op4.setText(j.getString("op4"));


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }






}
