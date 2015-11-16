package com.irishquiz.example.david.irishquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText login_text = (EditText) findViewById(R.id.editText);
        final EditText passwd_text = (EditText) findViewById(R.id.editText2);
        Button registerBTN = (Button) findViewById(R.id.op1);
        final DbManager db = new DbManager(this);


        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = login_text.getText().toString();
                String passwd = passwd_text.getText().toString();
                boolean check_login;

                if(login.equals("") || passwd.equals("")){
                    Toast.makeText(RegisterActivity.this, "Complete the data!", Toast.LENGTH_SHORT).show();
                }else{
                    check_login = db.onInsert_PLAYER(login,passwd);

                    if(check_login){
                        Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Login already exists. Try a new one!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}
