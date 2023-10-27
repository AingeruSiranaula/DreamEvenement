package com.talde3.dreamevenement.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.talde3.dreamevenement.R;
import com.talde3.dreamevenement.model.Erabiltzailea;

public class Etxea extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etxea);

        //Authentification instantzia eta logeatutako erabiltzailearen informazioa lortu
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent = getIntent();

        // Menua botoiak
        ImageButton imgGoitibeherakoaMenuaItxi = findViewById(R.id.imgGoitibeherakoaMenuaItxi);
        ImageButton imgGoitibeherakoaMenuaIreki = findViewById(R.id.imgGoitibeherakoaMenuaIreki);

        ImageButton imgBtnEzkontzak = findViewById(R.id.imgBtnEzkontzak);
        Button btnEzkontzak = findViewById(R.id.btnEzkontzak);

        ImageButton imgBtnProfila = findViewById(R.id.imgBtnProfila);
        Button btnProfila = findViewById(R.id.btnProfila);

        ImageButton imgBtnUrtebetetzeak = findViewById(R.id.imgBtnUrtebetetzeak);
        Button btnUrtebetetzeak = findViewById(R.id.btnUrtebetetzeak);

        ImageButton imgBtnJanak = findViewById(R.id.imgBtnJanak);
        Button btnJanak = findViewById(R.id.btnJanak);

        ImageButton imgBtnAfariak = findViewById(R.id.imgBtnAfariak);
        Button btnAfariak = findViewById(R.id.btnAfariak);

        ImageButton imgBtnJaunartzeakBataioak = findViewById(R.id.imgBtnJaunartzeakBataioak);
        Button btnJaunartzeakBataioak = findViewById(R.id.btnJaunartzeakBataioak);
        // Layout elementuak
        LinearLayout linearLayoutMenu = findViewById(R.id.linearLayoutMenu);
        LinearLayout PerfilaLayoutEtxea = findViewById(R.id.PerfilaLayoutEtxea);
        LinearLayout PerfilaLayoutEzkontzak = findViewById(R.id.PerfilaLayoutEzkontzak);
        LinearLayout PerfilaLayoutJaunartzeBataioak = findViewById(R.id.PerfilaLayoutJaunartzeBataioak);

        // Animo bezala logeatzean
        if (mAuth.getCurrentUser().isAnonymous()){
            PerfilaLayoutEtxea.setVisibility(View.GONE);
            PerfilaLayoutEzkontzak.setVisibility(View.VISIBLE);
            PerfilaLayoutJaunartzeBataioak.setVisibility(View.VISIBLE);
        }

        // Intent-eko informazioa lortu
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Erabiltzailea erabil =  (Erabiltzailea)bundle.getSerializable("erabiltzailea_mota");
                Log.i(TAG, erabil.getEnpresa().toString());
                if(!erabil.getEnpresa()){
                    PerfilaLayoutEzkontzak.setVisibility(View.VISIBLE);
                    PerfilaLayoutJaunartzeBataioak.setVisibility(View.VISIBLE);
                }
            }
        }

        imgGoitibeherakoaMenuaItxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutMenu.setVisibility(View.INVISIBLE);
            }
        });

        imgGoitibeherakoaMenuaIreki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutMenu.setVisibility(View.VISIBLE);
            }
        });
        View.OnClickListener clickListenerEzkontza = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Eskontzak.class);
                startActivity(intent);
            }
        };
        imgBtnEzkontzak.setOnClickListener(clickListenerEzkontza);
        btnEzkontzak.setOnClickListener(clickListenerEzkontza);


        View.OnClickListener clickListenerProfila = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Perfila.class);
                startActivity(intent);
            }
        };
        imgBtnProfila.setOnClickListener(clickListenerProfila);
        btnProfila.setOnClickListener(clickListenerProfila);


        View.OnClickListener clickListenerUrtebetzea = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Urtebetetzeak.class);
                startActivity(intent);
            }
        };
        imgBtnUrtebetetzeak.setOnClickListener(clickListenerUrtebetzea);
        btnUrtebetetzeak.setOnClickListener(clickListenerUrtebetzea);

        View.OnClickListener clickListenerJana = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Janak.class);
                startActivity(intent);
            }
        };
        imgBtnJanak.setOnClickListener(clickListenerJana);
        btnJanak.setOnClickListener(clickListenerJana);

        View.OnClickListener clickListenerAfaria = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Afariak.class);
                startActivity(intent);
            }
        };
        imgBtnAfariak.setOnClickListener(clickListenerAfaria);
        btnAfariak.setOnClickListener(clickListenerAfaria);

        View.OnClickListener clickListenerJaunartzeakBataioak = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, JaunartzeakBataioak.class);
                startActivity(intent);
            }
        };
        imgBtnJaunartzeakBataioak.setOnClickListener(clickListenerJaunartzeakBataioak);
        btnJaunartzeakBataioak.setOnClickListener(clickListenerJaunartzeakBataioak);



    }
}
