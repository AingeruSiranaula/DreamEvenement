package com.talde3.dreamevenement.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.talde3.dreamevenement.R;

public class Hasiera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasiera);

        // Automatikoki hurrengo activity-ra pasatzeko
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent bat sortzen dugu Login-era pasatu ahal izateko
                Intent intent= new Intent(Hasiera.this, Login.class);
                startActivity(intent);
                // Honek Hasiera ixten du, erabiltzaileak Atzera botoia erabiliz bertara itzultzea nahi ez baduzu.
                finish();
            }
        }, 1000); //
    }
}