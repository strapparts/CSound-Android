package com.strapparts.csound_android;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csounds.CsoundObj;
import com.csounds.bindings.ui.CsoundUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Example4 extends AppCompatActivity {

    private CsoundObj csoundObj;                            //state a CsoundObj
    CsoundUI csoundUI = null;                               //states CsoundUI and close evenctually a precedent CsoundUI object and states a CsoundUI object

    Button playMidi; //state button



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
        String path = getFilesDir() + getString(R.string.relative_path);           //variabile con il percorso in cui copiare i file, getFilesDir() è il percorso della memoria interna dell'app
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
                    String[] assets = {"do_la_re.mid", "marmstk1.wav", "read_midi_file.csd"};  //definizione array che contiene i nomi dei file da copiare
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
                        out.flush();
                        out.close();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);

        csoundObj = new CsoundObj();                                    //create csound object
        csoundUI = new CsoundUI(csoundObj);                             //create binding object

        copyFilesToInternalStorage();                                   //avvia procedura copia

        playMidi = (Button) findViewById(R.id.button14);
        playMidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csoundObj.startCsound(new File(getFilesDir() + getString(R.string.relative_path) + "read_midi_file.csd"));  //inserito percorso memoria interna per il file csd

            }
        });

    }
}
