package com.talde3.dreamevenement.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde3.dreamevenement.R;
import com.talde3.dreamevenement.model.Ekitaldia;

import java.util.ArrayList;

public class Eskontzak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eskontzak);
        // Datu baseko instantzia
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Datu baseko Eskontzak kolekzioko dokumentu guztien karga
        db.collection("Eskontzak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Ekitaldia> eskontzaList = new ArrayList<>(); // ArrayList non egongo diren gordeta Eskontza deberdinak

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Firestoreko dokumentoen informazioa berrezkuratu
                                String deskribapena = document.getString("Deskribapena");
                                ArrayList<Number> gonbidatuak = (ArrayList<Number>) document.get("Gonbidatuak");
                                int id = document.getLong("Id").intValue();
                                String izena = document.getString("Izena");
                                ArrayList<Number> prezioak = (ArrayList<Number>) document.get("Prezioak");
                                String argazkia = document.getString("Argazkia");

                                // Eskontza berri bat sortu lortutako datuekin
                                Ekitaldia eskontza = new Ekitaldia(deskribapena, gonbidatuak, id, izena, prezioak, argazkia);
                                // ArrayList barruan sartu sortutako eskontza
                                eskontzaList.add(eskontza);
                            }

                            LinearLayout layout = findViewById(R.id.layoutEskontzak);

                            //Eskontza bakoitzeko botoi berri bat sortzen da beraren izenarekin, qtea gainera Datu Basean daukan Id-a ematen dio
                            //activity_eskontzak.xml-ean sortutako
                            for (Ekitaldia eskontza : eskontzaList) {
                                ImageButton botoia = new ImageButton(Eskontzak.this);
                                String argazkia = eskontza.getArgazkia();
                                int idImagen = getResources().getIdentifier(argazkia, "drawable", getPackageName());
                                botoia.setImageResource(idImagen);
                                botoia.setId(eskontza.getId());

                                //Lekuen izena textview batean sartu
                                TextView izenaTextView = new TextView(Eskontzak.this);
                                izenaTextView.setText(eskontza.getIzena());
                                izenaTextView.setTextColor(Color.BLACK);
                                izenaTextView.setTextSize(20);
                                izenaTextView.setGravity(Gravity.CENTER);

                                //Argazkia botoiaren tamanaira ajutatuko da
                                botoia.setScaleType(ImageView.ScaleType.FIT_CENTER);

                                LinearLayout.LayoutParams layoutParamsImageButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600);
                                layoutParamsImageButton.setMargins(0, 0, 0, 20);
                                botoia.setLayoutParams(layoutParamsImageButton);
                                botoia.setBackgroundResource(android.R.color.transparent);


                                layout.addView(izenaTextView);
                                layout.addView(botoia);

                                //Sortutako botoien funtzionalitatea
                                botoia.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Eskontzak.this, Ekitaldiak.class);
                                        intent.putExtra("eskontza_id", eskontza);
                                        startActivity(intent);
                                    }
                                });
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}