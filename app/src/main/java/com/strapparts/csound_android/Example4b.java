package com.strapparts.csound_android;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Example4b extends BaseCsoundActivity {

    MediaPlayer mediaPlayer;
    Button buttonPlay, buttonPause;




    public void onBackPressed() {           //quando esci...

        // TODO Auto-generated method stub
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();             //ferma la riproduzione...
            Toast.makeText(Example4b.this,
                    "mediaPlayer.stop()",
                    Toast.LENGTH_LONG).show();
        }

        mediaPlayer = null;                //cancella l'oggetto mediaPlayer... (si fa così?)...
        Example4b.this.finish();           //e chiudi la pagina (torna alla pagina precedente)



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4b);

        mediaPlayer = MediaPlayer.create(this, R.raw.inno_alla_gioia);

        buttonPlay = (Button) findViewById(R.id.button16);
        buttonPause = (Button) findViewById(R.id.button17);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    Toast.makeText(Example4b.this,
                            "mediaPlayer.start()",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    Toast.makeText(Example4b.this,
                            "mediaPlayer.pause()",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
