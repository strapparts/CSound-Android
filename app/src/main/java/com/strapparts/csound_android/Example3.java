package com.strapparts.csound_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csounds.CsoundObj;
import com.csounds.CsoundObjListener;
import com.csounds.bindings.ui.CsoundUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Example3 extends BaseCsoundActivity  {


    private CsoundObj csoundObj;                            //state a CsoundObj
    CsoundUI csoundUI = null;                               //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object

    Button startCsoundAgain, suonaGen01, suonaGen01_b, suonaGen01_c, suonaGen01_d ; //state button


    //////////////////////////////////////////////
    // PROCEDURA COPIA FILE NELLA MEMORIA INTERNA DELL'APP ADATTATA DA QUELLA CHE COPIAVA NELLA MEMORIA ESTERNA, LA LASCIO DI RISERVA
    /*
    private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            Log.i("tag", "copyFileOrDir() "+path);
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath =  getFilesDir() + "/voices/" + path;
                Log.i("tag", "path="+fullPath);
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    if (!dir.mkdirs())
                        Log.i("tag", "could not create dir "+fullPath);
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copyFileOrDir( p + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in;
        OutputStream out;
        String newFileName = null;
        try {
            Log.i("tag", "copyFile() "+filename);
            in = assetManager.open(filename);
            if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = getFilesDir() + "/voices/" + filename.substring(0, filename.length()-4);
            else
                newFileName = getFilesDir() + "/voices/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of "+newFileName);
            Log.e("tag", "Exception in copyFile() "+e.toString());
        }

    }
    */
    // FINE PROCEDURA DI COPIA
    /////////////////////////////////////////////



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
                        Example3.this.finish();
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
        setContentView(R.layout.activity_example3);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                             //create binding object

        startCsoundAgain = (Button) findViewById(R.id.button12);
        suonaGen01 = (Button) findViewById(R.id.button10);              //connect button to widget
        suonaGen01_b = (Button) findViewById(R.id.button19);              //connect button to widget
        suonaGen01_c = (Button) findViewById(R.id.button20);              //connect button to widget
        suonaGen01_d = (Button) findViewById(R.id.button21);              //connect button to widget

        //startCsoundAgain.setEnabled(false);                             //bottoni disattivati
        //suonaGen01.setEnabled(false);

        startCsoundAgain.setEnabled(true);                              //completata la procedura di copia viene attivato il bottone start csound
        suonaGen01.setEnabled(false);
        suonaGen01_b.setEnabled(false);
        suonaGen01_c.setEnabled(false);
        suonaGen01_d.setEnabled(false);



        startCsoundAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csoundObj.startCsound(new File(getFilesDir() + getString(R.string.relative_path) + "test_03.csd"));  //inserito percorso memoria interna per il file csd

                startCsoundAgain.setEnabled(false);
                suonaGen01.setEnabled(true);
                suonaGen01_b.setEnabled(true);
                suonaGen01_c.setEnabled(true);
                suonaGen01_d.setEnabled(true);
            }
        });

        suonaGen01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event1 = "i1 0 .7 2";                     //prepare event for .csd score
                csoundObj.sendScore(event1);                     //sends event to .csd score

            }
        });


        suonaGen01_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event2 = "i1 0 .7 3";                     //prepare event for .csd score
                csoundObj.sendScore(event2);                     //sends event to .csd score

            }
        });

        suonaGen01_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event1 = "i1 0 .7 4";                     //prepare event for .csd score
                csoundObj.sendScore(event1);                     //sends event to .csd score

            }
        });

        suonaGen01_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event1 = "i1 0 .7 5";                     //prepare event for .csd score
                csoundObj.sendScore(event1);                     //sends event to .csd score

            }
        });


    }
}
