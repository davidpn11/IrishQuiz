package com.irishquiz.example.david.irishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Irish Quiz");
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText passwd = (EditText) findViewById(R.id.passwd);
        Button connect = (Button) findViewById(R.id.connBtn);
        Button register = (Button) findViewById(R.id.registerBtn);
        final DbManager db = new DbManager(this);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String result = getPlayer(login.getText().toString(),passwd.getText().toString());
                boolean check_login;
                check_login = db.login(login.getText().toString(),passwd.getText().toString());

                if(check_login){
                    Intent it = new Intent(MainActivity.this,MenuActivity.class);
                    it.putExtra("login",login.getText().toString());
                    startActivity(it);
                }
                else{
                    Toast.makeText(MainActivity.this, "Login and/or Password is not valid!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }

}
