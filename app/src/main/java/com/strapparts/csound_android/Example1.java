package com.strapparts.csound_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.csounds.CsoundObj;
import com.csounds.bindings.ui.CsoundUI;

public class Example1 extends BaseCsoundActivity {

    CsoundUI csoundUI = null;                               //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object
    private CsoundObj csoundObj;                            //state a CsoundObj
    Button CsoundStart, CsoundStop, PlayInstr, StopInstr ;  //state buttons
    SeekBar volumeCsound, volumeCsoundWind;                 //state slider

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);     //connect to layout window ("activity_main.xml")

        csoundObj = new CsoundObj();                                //create csound object
        csoundUI = new CsoundUI(csoundObj);                         //create binding object

        CsoundStart = (Button) findViewById(R.id.button2);  //connect java button to xml button
        CsoundStop = (Button) findViewById(R.id.button3);   //  "       "       "       "
        PlayInstr = (Button) findViewById(R.id.button4);    //  "       "       "       "
        StopInstr = (Button) findViewById(R.id.button5);    //  "       "       "       "
        volumeCsound = (SeekBar) findViewById(R.id.seekBar_volume);       //connect java seekbar to xml seekbar
        volumeCsoundWind = (SeekBar) findViewById(R.id.seekBar_vol_wind); //    "            "            "
        //TO SET SEEKBAR TO INITIAL VALUE DIFFERENTE TO '0', set value of 'progress' parameter in xml file with percent value

        CsoundStart.setEnabled(true); //button enabled at begin
        CsoundStop.setEnabled(false);
        PlayInstr.setEnabled(false);
        StopInstr.setEnabled(false);

        //CSOUND START BUTTON
        CsoundStart.setOnClickListener(new View.OnClickListener() { //prepare button to action
            @Override
            public void onClick(View v) {
                //write here your command for this button:
                csoundObj.startCsound(createTempFile(getResourceFileAsString(R.raw.test_01)));   //start Csound csd file
                csoundUI.addSlider(volumeCsound, "volume", 0, 1);          //connect java seekbar to csound chnget
                csoundUI.addSlider(volumeCsoundWind, "volume2", 0, 1);          //connect java seekbar to csound chnget
                CsoundStart.setEnabled(false);
                CsoundStop.setEnabled(true);
                PlayInstr.setEnabled(true);
                StopInstr.setEnabled(false);
            }
        });

        //CSOUND STOP BUTTON
        CsoundStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write here your command for this button:
                csoundObj.stop();           //stop the .csd file
                CsoundStart.setEnabled(true);
                CsoundStop.setEnabled(false);
                PlayInstr.setEnabled(false);
                StopInstr.setEnabled(false);
            }
        });

        //PLAY INSTRUMENT
        PlayInstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //write here your command for this button:
                String event = String.format("i1 0 -1");                     //state event for .csd score
                csoundObj.sendScore(event);                                 //send event to .csd score
                CsoundStart.setEnabled(false);
                CsoundStop.setEnabled(true);
                PlayInstr.setEnabled(false);
                StopInstr.setEnabled(true);
            }
        });

        //STOP INSTRUMENT
        StopInstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //write here your command for this button:
                String event = String.format("i1 0 1");                     //state event for .csd score
                csoundObj.sendScore(event);                                 //send event to .csd score
                CsoundStart.setEnabled(false);
                CsoundStop.setEnabled(true);
                PlayInstr.setEnabled(true);
                StopInstr.setEnabled(false);
            }
        });

    }

    public void onBackPressed() {
                        Example1.this.finish();
                        csoundObj.stop(); //in caso di uscita Csound viene fermato
    }
}
