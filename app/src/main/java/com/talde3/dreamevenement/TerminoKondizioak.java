package com.talde3.dreamevenement;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TerminoKondizioak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termino_kondizioak);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Ekitaldia e =  (Ekitaldia)bundle.getSerializable("eskontza_id");

        Log.i(TAG, e.getIzena());
    }
}