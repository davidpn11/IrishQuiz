package com.irishquiz.example.david.irishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


        int count = db.checkQuestionsNumber();

        if(count == 0){
            Log.v("QuestionsCheckup", "Adding questions");
            db.onInsert_QUESTION(
                    "According to legend, what do leprechauns make?",
                    "Wine",
                    "Shoes",
                    "Cheese",
                    "Hats",
                    2
            );

            db.onInsert_QUESTION(
                    "What is generally considered to be St. Patrick's birth name?",
                    "John",
                    "Andrew",
                    "Edward",
                    "Maewyn",
                    4
            );

            db.onInsert_QUESTION(
                    "Which colors are on the Irish flag?",
                    "Green red and white",
                    "Orange white and red",
                    "Green, white and orange",
                    "Orange red and green",
                    3
            );
            db.onInsert_QUESTION(
                    "What is the name of the largest freshwater lake in Ireland?",
                    "Talt",
                    "Finn",
                    "Beg",
                    "Neagh",
                    4
            );
            db.onInsert_QUESTION(
                    "What word do the Irish use instead of lake?",
                    "Loch",
                    "Laga",
                    "Lough",
                    "Fjord",
                    3
            );
            db.onInsert_QUESTION(
                    "What is the national bird of Ireland?",
                    "Diver",
                    "Great Skua",
                    "None",
                    "Cormorant",
                    3
            );
            db.onInsert_QUESTION(
                    "Which of these mythical creatures is a part of Irish culture?",
                    "Gins",
                    "Tennin",
                    "Makara",
                    "Fir Bolg",
                    4
            );
            db.onInsert_QUESTION(
                    "In the 1800s Ireland was hit with a famine. What food is associated with this event?",
                    "Cabbage",
                    "Beef",
                    "Bread",
                    "Potato",
                    4
            );
            db.onInsert_QUESTION(
                    "Prior to the twentieth century, leprechauns were often portrayed wearing which color?",
                    "Red",
                    "Yellow",
                    "Orange",
                    "Blue",
                    1
            );
            db.onInsert_QUESTION(
                    "If caught, what does a leprechaun offer to gain his release?",
                    "Tells the person's future",
                    "Takes his captor to fairyland",
                    "Three wishes",
                    "Forty pieces of silver",
                    3
            );
            db.onInsert_QUESTION(
                    "In Ireland, if you want the gift of the eloquence, what must you do?",
                    "Kiss a stone",
                    "Kiss a frog",
                    "Catch a leprechaun",
                    "Find a four leaf clover",
                    1
            );
            db.onInsert_QUESTION(
                    "Where is the famous Blarney Stone located?",
                    "Blarney Castle",
                    "Scone Abby",
                    "Windsor Castle",
                    "Buckingham Palace",
                    1
            );
            db.onInsert_QUESTION(
                    "What kind of rock is the Blarney Stone?",
                    "Bluestone",
                    "Slate",
                    "Sandstone",
                    "Granite",
                    1
            );
            db.onInsert_QUESTION(
                    "What is the capital of Northern Ireland?",
                    "Waterford",
                    "Limerick",
                    "Belfast",
                    "Cork",
                    3
            );
            db.onInsert_QUESTION(
                    "What is the lucky number of Ireland?",
                    "Thirteen",
                    "Three",
                    "Twelve",
                    "Seven",
                    2
            );
        }
        else{

            Log.v("QuestionsCheckup", "Questions already added");

        }



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
