package com.example.firstgame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.os.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton general;
    ImageButton manual;
    int healthcnt = 0;
    int generalcnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String healthy = "health";
        String genera = "general";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        manual = (ImageButton)findViewById(R.id.manual);
        general = (ImageButton)findViewById(R.id.begin);
        general.setOnClickListener(this);
        manual.setOnClickListener(this);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void onClick(View view){
       if(view.getId()==R.id.begin){
                general.setImageResource(R.drawable.begin2);
           new Handler().postDelayed(new Runnable() {
               public void run() {
                   general.setImageResource(R.drawable.beginbutton);
                   openGeneral();
               }
           }, 10);   //5 seconds


        }

        if(view.getId()==R.id.manual){
            manual.setImageResource(R.drawable.manual2);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    manual.setImageResource(R.drawable.bruhhh);
                    openManual();
                }
            }, 10);


        }

    }

    public void openGeneral(){
        Intent intent = new Intent(this, GeneralPage.class);
        startActivity(intent);
    }
    public void openManual(){
        Intent intent = new Intent(this, ManualPage.class);
        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnButtonClicked(View view) {
        TextView textView = (TextView)findViewById(R.id.textView);

    }*/
}
