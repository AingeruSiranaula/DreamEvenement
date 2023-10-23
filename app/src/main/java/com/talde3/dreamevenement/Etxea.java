package com.talde3.dreamevenement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Etxea extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etxea);

        ImageButton imgGoitibeherakoaMenuaItxi = findViewById(R.id.imgGoitibeherakoaMenuaItxi);
        ImageButton imgGoitibeherakoaMenuaIreki = findViewById(R.id.imgGoitibeherakoaMenuaIreki);
        LinearLayout linearLayoutMenu = findViewById(R.id.linearLayoutMenu);
        ImageButton imgBtnEzkontzak = findViewById(R.id.imgBtnEzkontzak);
        Button btnEzkontzak = findViewById(R.id.btnEzkontzak);
        ImageButton imgBtnProfila = findViewById(R.id.imgBtnProfila);
        Button btnProfila = findViewById(R.id.btnProfila);
        ImageButton imgBtnUrtebetetzeak = findViewById(R.id.imgBtnUrtebetetzeak);
        Button btnUrtebetetzeak = findViewById(R.id.btnUrtebetetzeak);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            imgBtnProfila.setVisibility(View.VISIBLE);
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
    }
}