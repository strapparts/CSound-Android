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
    Button startCsound, stopCsound, UpdateValueFromCsound;      //states Button
    ImageView noteImage, clefImage;                             //states ImageView
    TextView testValue;                                         //states TestView
    SeekBar volumeSlider;                                       //state slider

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                         //create binding object
        startCsound = (Button) findViewById(R.id.button6);              //connect java button a xml button widget
        stopCsound = (Button) findViewById(R.id.button7);               //connect java button a xml button widget
        UpdateValueFromCsound = (Button) findViewById(R.id.button);     //connect java button a xml button widget
        volumeSlider = (SeekBar) findViewById(R.id.seekBar);     //connect java seekbar to xml seekbar widget
        clefImage = (ImageView) findViewById(R.id.imageView2); //connect un widget xml a un oggetto java
        clefImage.setImageResource(R.drawable.ch_40x80); //show clef image
        noteImage = (ImageView) findViewById(R.id.imageView); //connect un widget xml a un oggetto java
        noteImage.setImageResource(R.drawable.f4_40x80); //show image
        testValue = (TextView) findViewById(R.id.textView2);


        CsoundUI ui = new CsoundUI(csoundObj);

        //TODO per Andrea: impostare il bottone a 0 e inserire un trigger su csound
        ui.addButton(UpdateValueFromCsound, "button-channel-1", 1); //a cosa corrisponde il numero?? Ci deve stare un numero
        //"button-channel-1" is a name of channels in .csd file

        csoundObj.addBinding(new CsoundBinding() {
            CsoundObj csoundObj = null;
            public void setup(CsoundObj csoundObj) {
                //Log.d("CsoundAndroidActivity", "ValueCacheable setup called");
                this.csoundObj = csoundObj;
            }

            public void updateValuesToCsound() {
            }

            public void updateValuesFromCsound() {
                //Csound csound = csoundObj.getCsound();
                //float imageValue =  csoundObj.getCsound().GetChannel("setImage");
                //Log.d("CsoundAndroidActivity", "ValueCacheable From called");
                runOnUiThread(new Runnable() {
                    public void run()
                    {
                        if (csoundObj.getOutputChannelPtr("setImage", controlChannelType.CSOUND_CONTROL_CHANNEL).GetValue(0) == 99)
                            //"setImage" is a name of channels in .csd file
/*
With respect to the channeltype number, here are the constants:
                            enum  controlChannelType {
                                CSOUND_CONTROL_CHANNEL = 1,
                                CSOUND_AUDIO_CHANNEL = 2,
                                CSOUND_STRING_CHANNEL = 3,
                                CSOUND_PVS_CHANNEL = 4,
                                CSOUND_VAR_CHANNEL = 5,
                                CSOUND_CHANNEL_TYPE_MASK = 15,
                                CSOUND_INPUT_CHANNEL = 16,
                                CSOUND_OUTPUT_CHANNEL = 32
                            }
  */

                        {
                            noteImage.setImageResource(R.drawable.a4_40x80);
                            testValue.setText("a4");
                            Log.d("CsoundAndroidActivity", "a4");
                        }
                        else
                        {
                            noteImage.setImageResource(R.drawable.f4_40x80);
                            testValue.setText("f4");
                            Log.d("CsoundAndroidActivity", "f4");
                        }
                    }
                });

            }

            public void cleanup() {
                //Log.d("CsoundAndroidActivity", "ValueCacheable cleanup called");
                //csoundObj = null;
            }
        });

        startCsound.setOnClickListener(new View.OnClickListener() { //connect actions to start button
            @Override
            public void onClick(View v) {
                //CsoundUI csoundUI = new CsoundUI(csoundObj);
                //csoundObj.startCsound((createTempFile(getResourceFileAsString(R.raw.test_04))));   //runs .csd file

                csoundObj.startCsound((createTempFile(getResourceFileAsString(R.raw.test_02))));   //start Csound csd file
                csoundUI.addSlider(volumeSlider, "volume", 0, 1);          //connect java seekbar to csound chnget
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
