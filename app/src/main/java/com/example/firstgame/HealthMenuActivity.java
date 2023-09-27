package com.example.firstgame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.view.View.OnClickListener;

public class HealthMenuActivity extends AppCompatActivity{

    Button heartrate;
    Button cal;
    Button other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_menu);
        heartrate = (Button)findViewById(R.id.HRbutton);
        cal = (Button)findViewById(R.id.cal);
        other = (Button)findViewById(R.id.otherbutton);
        /*heartrate.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(HealthMenuActivity.this,HealthPage.class);
                startActivity(i);
            });
        });
        cal.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(HealthMenuActivity.this,HealthPage.class);
                startActivity(i);
            }
        });
        other.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(HealthMenuActivity.this,HealthPage.class);
                startActivity(i);
            }
        });*/
    }
}

