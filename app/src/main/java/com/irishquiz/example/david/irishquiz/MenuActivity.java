package com.irishquiz.example.david.irishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Irish Quiz");

        TextView player_name = (TextView) findViewById(R.id.textView1);
        TextView score = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        Intent it = getIntent();
        String p_name = it.getStringExtra("login");
        final DbManager db = new DbManager(this);
        player_name.setText(player_name.getText() + p_name);

        String getScore = db.getScore(p_name);

        if(!getScore.equals(null)){
            score.setText(score.getText()+getScore);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,GameActivity.class));
            }
        });

    }
}