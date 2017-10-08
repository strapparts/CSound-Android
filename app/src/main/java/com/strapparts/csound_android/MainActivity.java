package com.strapparts.csound_android;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends BaseCsoundActivity {

    Button buttonCopyFiles, buttonToPageOne, buttonToPageTwo, buttonToPageThree, buttonToPageFour, buttonToPageFour_b, buttonExamplesSynth; //declare buttons



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
                    String[] assets = {"a4_pianoforte.wav", "c5_pianoforte.wav", "f4_pianoforte.wav", "g4_pianoforte.wav", "test_03.csd", "do_la_re.mid", "marmstk1.wav", "test_04.csd", "libfluidOpcodes.so", "flauto_dritto.sf2", "synth_sounds_07.csd"};  //definizione array che contiene i nomi dei file da copiare
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






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //connect to layout window ("activity_main.xml")

        //se manca una di queste connessioni l'applicazione si chiude subito, anche se non vengono segnalati errori!!!!!!!!!!!!!!
        buttonCopyFiles = (Button) findViewById(R.id.button18);   //connect java button to xml button image widget
        buttonToPageOne = (Button) findViewById(R.id.button1);   //connect java button to xml button image widget
        buttonToPageTwo = (Button) findViewById(R.id.button8);   //connect java button to xml button image widget
        buttonToPageThree = (Button) findViewById(R.id.button9);   //connect java button to xml button image widget
        buttonToPageFour = (Button) findViewById(R.id.button11);   //connect java button to xml button image widget
        buttonToPageFour_b = (Button) findViewById(R.id.button15);   //connect java button to xml button image widget
        buttonExamplesSynth = (Button) findViewById(R.id.button22);

        //buttonCopyFiles.setEnabled(true); it's not necessary, it's true by deafault
        buttonToPageOne.setEnabled(false);
        buttonToPageTwo.setEnabled(false);
        buttonToPageThree.setEnabled(false);
        buttonToPageFour.setEnabled(false);
        buttonToPageFour_b.setEnabled(false);
        buttonExamplesSynth.setEnabled(false);



        buttonCopyFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                copyFilesToInternalStorage();                                   //avvia procedura copia

                buttonCopyFiles.setEnabled(false);
                buttonToPageOne.setEnabled(true);
                buttonToPageTwo.setEnabled(true);
                buttonToPageThree.setEnabled(true);
                buttonToPageFour.setEnabled(true);
                buttonToPageFour_b.setEnabled(true);
                buttonExamplesSynth.setEnabled(true);

            }
        });


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
                Intent gotoplay1 = new Intent(getApplicationContext(), Example3.class); //prepare and start the activity "Example3"
                startActivity(gotoplay1);
            }
        });

        buttonToPageFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example4.class); //prepare and start the activity "Example4"
                startActivity(gotoplay1);
            }
        });

        buttonToPageFour_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example4b.class); //prepare and start the activity "Example4_b"
                startActivity(gotoplay1);
            }
        });

        buttonExamplesSynth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoplay1 = new Intent(getApplicationContext(), Example5.class); //prepare and start the activity "Example5"
                startActivity(gotoplay1);
            }
        });


    }
}
