package com.strapparts.csound_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonToPageOne, buttonToPageTwo, buttonToPageThree; //declare buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //connect to layout window ("activity_main.xml")

        buttonToPageOne = (Button) findViewById(R.id.button1);   //connect java button to xml button image widget
        buttonToPageTwo = (Button) findViewById(R.id.button8);   //connect java button to xml button image widget
        buttonToPageThree = (Button) findViewById(R.id.button9);   //connect java button to xml button image widget

        buttonToPageOne.setOnClickListener(new View.OnClickListener() { //prepare button to action
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example1.class); //prepare and start the activity "Example1"
                startActivity(gotoplay1);
            }
        });

        buttonToPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example2.class); //prepare and start the activity "Example2"
                startActivity(gotoplay1);
            }
        });

        buttonToPageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example3.class); //prepare and start the activity "Example2"
                startActivity(gotoplay1);
            }
        });

    }
}
