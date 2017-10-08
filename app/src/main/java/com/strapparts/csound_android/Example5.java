package com.strapparts.csound_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.csounds.CsoundObj;
import com.csounds.CsoundObjListener;
import com.csounds.bindings.ui.CsoundUI;

public class Example5 extends BaseCsoundActivity implements CsoundObjListener {

    CsoundUI csoundUI = null;                                   //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object
    private CsoundObj csoundObj;                                //states csound

    SeekBar volumeCsound;                 //state slider
    Button buttonStart, buttonStop;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example5);

        volumeCsound = (SeekBar) findViewById(R.id.seekBar2);
        buttonStart = (Button) findViewById(R.id.button23);
        buttonStop = (Button) findViewById(R.id.button24);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                             //create binding object
        csoundObj.startCsound(createTempFile(getResourceFileAsString(R.raw.synth_sounds_07)));   //start Csound csd file
        csoundUI.addSlider(volumeCsound, "volume", 0, 1);          //connect java seekbar to csound chnget


        /*
        buttonStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    String event1 = String.format("i 4 0 -1 72 1");                     //state event for .csd score
                    csoundObj.sendScore(event1);                                 //send event to .csd score
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    String event2 = String.format("i 90 0 .01 4 .05");                     //state event for .csd score
                    csoundObj.sendScore(event2);                                 //send event to .csd score
                }
                return false;
            }
        });
        */

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = String.format("i 10 0 -1 72 1");                     //state event for .csd score

                csoundObj.sendScore(event);                                 //send event to .csd score
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = String.format("i 90 0 .01 10 .1");                     //state event for .csd score
                csoundObj.sendScore(event);                                 //send event to .csd score
            }
        });



    }

    public void onBackPressed() {
        Example5.this.finish();
        csoundObj.stop(); //in caso di uscita Csound viene fermato
    }

    public void csoundObjStarted(CsoundObj csoundObj) {}            //needs "implements CsoundObjListener"


    public void csoundObjCompleted(CsoundObj csoundObj) {            //needs "implements CsoundObjListener"
    }


}
