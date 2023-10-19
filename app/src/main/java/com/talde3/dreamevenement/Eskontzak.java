package com.talde3.dreamevenement;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Eskontzak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eskontzak);


        db.collection("Eskontzak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Eskontza> eskontzaList = new ArrayList<>(); // ArrayList non egongo diren gordeta Eskontza deberdinak

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Recupera los datos del documento de Firestore
                                String deskribapena = document.getString("Deskribapena");
                                ArrayList<Number> gonbidatuak = (ArrayList<Number>) document.get("Gonbidatuak");
                                int id = document.getLong("Id").intValue();
                                String izena = document.getString("Izena");
                                ArrayList<Number> prezioak = (ArrayList<Number>) document.get("Prezioak");

                                // Eskontza berri bat sortu lortutako datuekin
                                Eskontza eskontza = new Eskontza(deskribapena, gonbidatuak, id, izena, prezioak);
                                // ArrayList barruan sartu sortutako eskontza
                                eskontzaList.add(eskontza);
                            }

                            LinearLayout layout = findViewById(R.id.layoutEskontzak);

                            //Eskontza bakoitzeko botoi berri bat sortzen da beraren izenarekin, qtea gainera Datu Basean daukan Id-a ematen dio
                            //activity_eskontzak.xml-ean sortutako
                            for (Eskontza eskontza : eskontzaList) {
                                Button botoia = new Button(Eskontzak.this);
                                botoia.setText(eskontza.getIzena());
                                botoia.setId(eskontza.getId());
                                botoia.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                layout.addView(botoia);

                                //Sortutako botoien funtzionalitatea
//                                botoia.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(Eskontzak.this, TerminoKondizioak.class);
//                                        startActivity(intent);
//                                    }
//                                });
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}