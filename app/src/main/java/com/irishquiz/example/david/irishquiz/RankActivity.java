package com.irishquiz.example.david.irishquiz;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

class RankAdapter extends ArrayAdapter<String>{

    ArrayList<String> data;
    Context context;
    int layoutResourceId;


    public RankAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            TextView pos = (TextView) view.findViewById(R.id.position);
            TextView player = (TextView) view.findViewById(R.id.player);
            TextView score_text = (TextView) view.findViewById(R.id.score);

            String full = data.get(position);
            String name="",score="";




            for(int i=0; i < full.length();i++){

                if(full.substring(i,i+1).equals("-")){
                    score = full.substring(i+1,i+2);
                    break;
                }else{
                    name += full.substring(i,i+1);
                }
            }




            pos.setText(""+(position+1));
            player.setText(name);
            score_text.setText(score);
        }

        return view;
    }

}

public class RankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setTitle("Quiz Rank");
        ListView list = (ListView) findViewById(R.id.listView);
        final DbManager db = new DbManager(this);


        ArrayList<String> array = db.getAllScores();


        RankAdapter adapter2 = new RankAdapter(this,R.layout.row,array);
        /*ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
               this, // The current context (this activity)
              android.R.layout.simple_list_item_1, // The name of the layout ID.
               array);*/

        list.setAdapter(adapter2);



    }
}
