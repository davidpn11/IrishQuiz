package com.irishquiz.example.david.irishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Your Score");

        final DbManager db = new DbManager(this);
        TextView result = (TextView) findViewById(R.id.result);
        Button menu = (Button) findViewById(R.id.menu);
        Button rank = (Button) findViewById(R.id.rank);
        Intent it = getIntent();
        final String p = it.getStringExtra("player");
        int s = it.getIntExtra("score", 0);

        boolean change = db.setScore(p, s);

        String res = "You got "+s+" points!\n";

        if(change){
            res += "New Highest score!";
        }
        else{
            res += "You can do better!";
        }
        result.setText(res);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("login", p);
                startActivity(intent);
            }
        });

        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,RankActivity.class));
            }
        });

    }
}
