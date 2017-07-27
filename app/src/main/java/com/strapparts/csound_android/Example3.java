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

    Button startCsoundAgain, suonaGen01; //state button


    private void copyFilesToInternalStorage(){
        //copyFileOrDir(""); // utilizzata procedura di copia modificata a partire da quella che copia nella memoria esterna

        ///// PICCOLA PROCEDURA CHE ELENCA I FILE CONTENUTI NELLA MEMORIA INTERNA, UTILIZZATA PER IL DEBUG LA LASCIO IN CASO SERVA
        /*
        if (getFilesDir().list().length==0){
            Log.i("tag", "memoria interna vuota");
        } else {
            for (String filename : getFilesDir().list()) {
                Log.i("tag", filename);
            }
        }
        */


        //////////////////////////////////////////////////////////////////////////
        //PROCEDURA DI COPIA DEI FILE FATTA DA ANTONIO, FUNZIONANTE

        Log.i("debug","inizio procedura");
        String path = getFilesDir() + getString(R.string.relative_path);           //variabile con il percorso nel cui copiare i file, getFilesDir() è il percorso della memoria interna dell'app
        Log.i("debug","path="+path);
        File dir=new File(path);                            //istanziamento del percorso come oggetto in modo da poter usare il metodo exists()
        try {
            if (!dir.exists()) {                                 //controllo esistenza directory: se non esiste procedo alla copia dei file
                Log.i("tag", "creazione cartella avviata");
                if (!dir.mkdirs()) {                               //creazione cartella "voices"
                    Log.i("tag", "creazione cartella non riuscita");
                } else {
                    //procedura di copia di tutti gli asset nella directory interna
                    AssetManager assetManager = this.getAssets();            //inizializzazione asset manager
                    String[] assets = {"a4_pianoforte.wav", "c5_pianoforte.wav", "f4_pianoforte.wav", "g4_pianoforte.wav", "test_03.csd"};  //definizione array che contiene i nomi dei file da copiare
                    InputStream in;                              //definizioni input e output stream per la copia dei file
                    OutputStream out;

                    for (String filename:assets) {                   //ciclo for per scorrere l'elenco
                        Log.i("tag", "copyFile() " + filename + " to " + path + filename);
                        in = assetManager.open(filename);                   //caricamento nell'inputstream del file dagli assets
                        out = new FileOutputStream(path+filename);        //creazione di un outputstream per il file da creare nella memoria interna
                        byte[] buffer = new byte[1024];                     //procedura di copia byte per byte del file
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        in.close();                                         //pulizia e azzeramento degli stream
                        in = null;
                        out.flush();
                        out.close();
                        out = null;

                    }
                }
            }
            else{
                Log.i("debug","cartella già esistente, chiusura procedura");
            }
        }
        catch (IOException e) {
            Log.e("tag", "I/O Exception", e);
        }

        //FINE PROCEDURA COPIA ANTONIO
        ////////////////////////////////////////////
    }

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

        startCsoundAgain.setEnabled(false);                             //bottoni disattivati
        suonaGen01.setEnabled(false);

        copyFilesToInternalStorage();                                   //avvia procedura copia

        startCsoundAgain.setEnabled(true);                              //completata la procedura di copia viene attivato il bottone start csound
        suonaGen01.setEnabled(false);



        startCsoundAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csoundObj.startCsound(new File(getFilesDir() + getString(R.string.relative_path) + "test_03.csd"));  //inserito percorso memoria interna per il file csd

                startCsoundAgain.setEnabled(false);
                suonaGen01.setEnabled(true);
            }
        });

        suonaGen01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event = String.format("i1 0 .7");                     //prepare event for .csd score
                csoundObj.sendScore(event);                                  //sends event to .csd score

            }
        });


    }
}
