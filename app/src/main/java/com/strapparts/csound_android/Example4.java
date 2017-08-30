package com.strapparts.csound_android;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.csounds.CsoundObj;
import com.csounds.bindings.ui.CsoundUI;

import java.io.File;

public class Example4 extends BaseCsoundActivity {

    private CsoundObj csoundObj;                            //state a CsoundObj
    CsoundUI csoundUI = null;                               //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object

    Button playMidi; //state button




    ///////////////////////
    ///////////////////////
    //RICHIESTA DI CONFERMA DI USCITA con metodo onBackPressed() E STOP CSOUND
    //da: http://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirmtoexit))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        //VariabiliDiClasse.value3 = 0; //contatore risposte errate da azzerare nel caso di uscita
                        //VariabiliDiClasse.count3 = 0;
                        //VariabiliDiClasse.count4 = 0;
                        //count4 = 0; //contatore risposte esatte da azzerare nel caso di uscita
                        //value3 = 0;
                        //permutTab = null;
                        Example4.this.finish();
                        csoundObj.stop(); //in caso di uscita Csound viene fermato, ma viene anche cancellato?

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    ///////////////////////
    ///////////////////////



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                             //create binding object


        playMidi = (Button) findViewById(R.id.button14);
        playMidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csoundObj.startCsound(new File(getFilesDir() + getString(R.string.relative_path) + "test_04.csd"));  //inserito percorso memoria interna per il file csd

            }
        });

    }
}
