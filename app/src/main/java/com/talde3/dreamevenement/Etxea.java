package com.talde3.dreamevenement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Etxea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etxea);

        ImageButton imgbtnEskontzaEtxea = findViewById(R.id.imgbtnEskontzaEtxea);
        ImageButton imgbtnPerfilaEtxea = findViewById(R.id.imgbtnPerfilaEtxea);


        imgbtnEskontzaEtxea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Eskontzak.class);
                startActivity(intent);
            }
        });

        imgbtnPerfilaEtxea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Etxea.this, Perfila.class);
                startActivity(intent);
            }
        });
    }

}