package com.irishquiz.example.david.irishquiz;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView text1=(TextView)findViewById(R.id.textView1);




        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                text1.setText("0");
                Toast.makeText(GameActivity.this, "The game has ended!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}
