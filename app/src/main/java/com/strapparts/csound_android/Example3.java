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

    Button buttoncopyWavToCard, startCsoundAgain, suonaGen01; //state button

/*
///////////////////////////////// METODO PER VERIFICARE LA COPIA DEI FILE NELLA CARTELLA ASSETS NELLA CARTELLA DELLA SDCARD
//da: https://stackoverflow.com/questions/3592836/check-for-file-existence-in-androids-assets-folder/7337516
    //Context context = getApplicationContext();
    //Context AppContext = getApplicationContext();  //getApplicationContext(), getContext(), getBaseContext()

    private boolean isAssetExists(String pathInAssetsDir){
        //AssetManager assetManager = context.getResources().getAssets();
        AssetManager assetManager = AppContext.get().getResources().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(pathInAssetsDir);
            if(null != inputStream ) {
                return true;
            }
        }  catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
/////////////////////////////////
*/


//TODO per Antonio: sostituire la procedura di copia su memoria esterna con copia su cache
    ////////////////////////////////////////////////////////
//YOU MUST COPY GEN01 FILE WAV IN THE SOUND CARD, BECAUSE OF READING RESTRICTIONS
//COPIARE I FILE CONTENUTI IN 'ASSETS' NELLA 'SDCARD', PER POTER UTILIZZARE LA FUNZIONE 'GEN01'
// ALTRIMENTI INUTILIZZABILE A CAUSA DI RESTRIZIONI DI LETTURA
    ///////////////////////////////////////////////////////
    /////////////////////////////////////////////////////// TO COPY FILES FROM 'ASSETS' FOLDER TO SDCARD
    //from: http://stackoverflow.com/questions/4447477/how-to-copy-files-from-assets-folder-to-sdcard
    final static String TARGET_BASE_PATH = Environment.getExternalStorageDirectory() + "/MyAppCsound/voices/";
    File csdPath = new File(TARGET_BASE_PATH + "test_03.csd"); //il .csd file che viene letto

    private void copyFilesToSdCard() {
        copyFileOrDir(""); // copy all files in assets folder in my project
    }

    private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            Log.i("tag", "copyFileOrDir() "+path);
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath =  TARGET_BASE_PATH + path;
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

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            Log.i("tag", "copyFile() "+filename);
            in = assetManager.open(filename);
            if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = TARGET_BASE_PATH + filename.substring(0, filename.length()-4);
            else
                newFileName = TARGET_BASE_PATH + filename;
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
/////////////////////////////////////////////
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

        csoundObj = new CsoundObj();                                //create csound object
        csoundUI = new CsoundUI(csoundObj);                         //create binding object

        buttoncopyWavToCard = (Button) findViewById(R.id.button11); //connect button to widget
        suonaGen01 = (Button) findViewById(R.id.button10); //connect button to widget
        startCsoundAgain = (Button) findViewById(R.id.button12);

        buttoncopyWavToCard.setEnabled(true);
        startCsoundAgain.setEnabled(true);
        suonaGen01.setEnabled(false);


        buttoncopyWavToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*              //TENTATIVO NON RIUSCITO
                if (isAssetExists("/sdcard/MyAppCsound/voices/") == true)           //String TARGET_BASE_PATH = "/sdcard/MyAppCsound/voices/"
                    buttoncopyWavToCard.setEnabled(false);
                    else
                    buttoncopyWavToCard.setEnabled(true);
*/
                //copyWavOnCard.getClass();                                       //dopo cruento corpo a corpo con Android **********************************
                copyFilesToSdCard();                              //sembra che il metodo viene richiamato dalla classe 'CopyToSDCard' ***************************
                                                                                //copy 'assets' content to "/sdcard/MyAppCsound/voices/"
                //csoundObj.startCsound(csdPath);                             //start Csound .csd file
            }
        });

        startCsoundAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                csoundObj.startCsound(csdPath);                             //start Csound .csd file

                buttoncopyWavToCard.setEnabled(false);
                startCsoundAgain.setEnabled(false);
                suonaGen01.setEnabled(true);
            }
        });

        suonaGen01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String event = String.format("i1 0 .7");                     //prepare event for .csd score
                csoundObj.sendScore(event);                                 //sends event to .csd score

            }
        });


    }
}
