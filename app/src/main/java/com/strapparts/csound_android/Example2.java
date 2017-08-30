package com.strapparts.csound_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.csounds.CsoundObj;
import com.csounds.CsoundObjListener;
import com.csounds.bindings.CsoundBinding;
import com.csounds.bindings.ui.CsoundUI;

import csnd6.controlChannelType;

public class Example2 extends BaseCsoundActivity implements
        CsoundObjListener {

    CsoundUI csoundUI = null;                                   //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object
    private CsoundObj csoundObj;                                //states csound
    Button startCsound, stopCsound, UpdateValueWithSwitch, UpdateValueWithPush;      //states Button
    ImageView noteImage1, clefImage1, noteImage2, clefImage2;                       //states ImageView
    TextView testValue1, testValue2;                                         //states TestView
    SeekBar volumeSlider;                                       //state slider

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                             //create binding object
        startCsound = (Button) findViewById(R.id.button6);              //connect java button a xml button widget
        stopCsound = (Button) findViewById(R.id.button7);               //connect java button a xml button widget
        UpdateValueWithSwitch = (Button) findViewById(R.id.button);     //connect java button a xml button widget
        UpdateValueWithPush = (Button) findViewById(R.id.button13);     //connect java button a xml button widget
        volumeSlider = (SeekBar) findViewById(R.id.seekBar);     //connect java seekbar to xml seekbar widget
        //TO SET SEEKBAR TO INITIAL VALUE DIFFERENTE TO '0', set value of 'progress' parameter in xml file with percent value

        clefImage1 = (ImageView) findViewById(R.id.imageView2); //connect un widget xml a un oggetto java
        clefImage1.setImageResource(R.drawable.ch_40x80); //show clef image
        noteImage1 = (ImageView) findViewById(R.id.imageView); //connect un widget xml a un oggetto java (without this line of code the next line doesn't make error, but activity shut down)
        noteImage1.setImageResource(R.drawable.f4_40x80); //show image
        testValue1 = (TextView) findViewById(R.id.textView2);

        clefImage2 = (ImageView) findViewById(R.id.imageView3); //connect un widget xml a un oggetto java (without this line of code the next line doesn't make error, but activity shut down)
        clefImage2.setImageResource(R.drawable.ch_40x80);   //show image
        noteImage2 = (ImageView) findViewById(R.id.imageView4);
        noteImage2.setImageResource(R.drawable.c5_40x80);
        testValue2 = (TextView) findViewById(R.id.textView3);

        CsoundUI ui = new CsoundUI(csoundObj);





        ui.addButton(UpdateValueWithSwitch, "button-channel-1", 0); //col valore '0': il bottone emette un solo '1' nell'istante in cui viene premuto.
        ui.addButton(UpdateValueWithPush, "button-channel-2", 1);  //col valore '1': il bottone emette un flusso di '1' fintanto che il bottone viene premuto.
        //"button-channel-1" is a name of channels in .csd file


        //TODO per Antonio: fare in modo che i due seguenti addBinding possano rimanere attivi contemporaneamente

//================================== INIZIO primo addBinding ==============================


        csoundObj.addBinding(new CsoundBinding() {
            CsoundObj csoundObj = null;
            public void setup(CsoundObj csoundObj) {
                //Log.d("CsoundAndroidActivity", "ValueCacheable setup called");
                this.csoundObj = csoundObj;
            }

            public void updateValuesToCsound() {
            }

            public void updateValuesFromCsound() {          //example of method updateValuesFromCsound()
                //Csound csound = csoundObj.getCsound();
                //float imageValue =  csoundObj.getCsound().GetChannel("setImage");
                //Log.d("CsoundAndroidActivity", "ValueCacheable From called");
                runOnUiThread(new Runnable() {              //thread activation
                    public void run()
                    {
                        if (csoundObj.getOutputChannelPtr("setImage", controlChannelType.CSOUND_CONTROL_CHANNEL).GetValue(0) == 99)
                            //"setImage" is a name of channels in .csd file
                        {
                            noteImage1.setImageResource(R.drawable.a4_40x80);
                            testValue1.setText("a4");
                            Log.d("CsoundAndroidActivity", "a4");
                        }
                        else
                        {
                            noteImage1.setImageResource(R.drawable.f4_40x80);
                            testValue1.setText("f4");
                            Log.d("CsoundAndroidActivity", "f4");
                        }

                        if (csoundObj.getOutputChannelPtr("setImage2", controlChannelType.CSOUND_CONTROL_CHANNEL).GetValue(0) == 99)
                        {
                            noteImage2.setImageResource(R.drawable.c5_40x80);
                            testValue2.setText("c5");
                            Log.d("CsoundAndroidActivity", "c5");
                        }
                        else
                        {
                            noteImage2.setImageResource(R.drawable.g4_40x80);
                            testValue2.setText("g4");
                            Log.d("CsoundAndroidActivity", "g4");
                        }
                    }
                });
            }


            public void cleanup() {
                //Log.d("CsoundAndroidActivity", "ValueCacheable cleanup called");
                //csoundObj = null;
            }
        });


//================================== FINE primo addBinding ==============================




//================================== INIZIO secondo addBinding ==============================
/*

        csoundObj.addBinding(new CsoundBinding() {
            CsoundObj csoundObj = null;
            public void setup(CsoundObj csoundObj) {
                //Log.d("CsoundAndroidActivity", "ValueCacheable setup called");
                this.csoundObj = csoundObj;
            }

            public void updateValuesToCsound() {
            }

            public void updateValuesFromCsound() {          //example of method updateValuesFromCsound()
                //Csound csound = csoundObj.getCsound();
                //float imageValue =  csoundObj.getCsound().GetChannel("setImage");
                //Log.d("CsoundAndroidActivity", "ValueCacheable From called");


                runOnUiThread(new Runnable() {              //thread activation
                    public void run()
                    {


                        if (csoundObj.getOutputChannelPtr("setImage2", controlChannelType.CSOUND_CONTROL_CHANNEL).GetValue(0) == 99)
                        {
                            noteImage2.setImageResource(R.drawable.c5_40x80);
                            testValue2.setText("c5");
                            Log.d("CsoundAndroidActivity", "c5");
                        }
                        else
                        {
                            noteImage2.setImageResource(R.drawable.g4_40x80);
                            testValue2.setText("g4");
                            Log.d("CsoundAndroidActivity", "g4");
                        }

                    }
                });


            }

            public void cleanup() {
                //Log.d("CsoundAndroidActivity", "ValueCacheable cleanup called");
                //csoundObj = null;
            }
        });

*/

//================================== FINE secondo addBinding ==============================








        startCsound.setOnClickListener(new View.OnClickListener() { //connect actions to start button
            @Override
            public void onClick(View v) {
                //CsoundUI csoundUI = new CsoundUI(csoundObj);
                //csoundObj.startCsound((createTempFile(getResourceFileAsString(R.raw.test_04))));   //runs .csd file

                csoundObj.startCsound((createTempFile(getResourceFileAsString(R.raw.test_02))));   //start Csound csd file
                csoundUI.addSlider(volumeSlider, "volume", 0, 1);          //connect java seekbar to csound chnget (default value set in widget, percent value on 'progress')
                //"volume" is a name of channels in .csd file

            }
        });

        stopCsound.setOnClickListener(new View.OnClickListener() {  //connect actions to stop button
            @Override
            public void onClick(View v) {
                csoundObj.stop();           //stops the .csd file
            }
        });

    }

    public void csoundObjStarted(CsoundObj csoundObj) {}            //needs "implements CsoundObjListener"


    public void csoundObjCompleted(CsoundObj csoundObj) {            //needs "implements CsoundObjListener"
        /*
        handler.post(new Runnable() {
            public void run() {
                //startCsound.setChecked(false);
            }
        });
        */
    }

}
