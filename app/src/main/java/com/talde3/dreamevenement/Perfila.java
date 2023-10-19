package com.talde3.dreamevenement;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Perfila extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfila);

        // Datu base konexioa
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        int duration = Toast.LENGTH_SHORT;
        // Errore mezuak
        Toast error_field = Toast.makeText(Perfila.this, getString(R.string.errorField), duration);
        Toast message_update = Toast.makeText(Perfila.this, getString(R.string.messageUpdate), duration);

        // Layout elementuak
        Button btnLogOut = findViewById(R.id.btnLogOut);
        Button btnGordePerfila = findViewById(R.id.btnGordePerfila);
        Button btnEditatuPerfila = findViewById(R.id.btnEditatuPerfila);

        EditText nifEnpresa = findViewById(R.id.edtNifEnpresaPerfila);
        EditText izenaEnpresa = findViewById(R.id.edtEnpresaIzenaPerfila);
        EditText emailEnpresa = findViewById(R.id.edtEmailEnpresaPerfila);
        EditText telEnpresa = findViewById(R.id.edtTelEnpresaPerfila);
        EditText nanEnpresa = findViewById(R.id.edtNanEnpresaPerfila);

        // Erabiltzailearen datuk datu basetik hartu
        DocumentReference docRef = db.collection("Erabiltzaileak").document(currentUser.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Datuak erabiltzaile objetu bihurtu
                Erabiltzailea e = documentSnapshot.toObject(Erabiltzailea.class);

                nifEnpresa.setText(e.getNif());
                izenaEnpresa.setText(e.getIzena());
                emailEnpresa.setText(e.getEmail());
                telEnpresa.setText(e.getTelefonoa());
                nanEnpresa.setText(e.getNan());
            }
        });

        // Edizio botoia
        btnEditatuPerfila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Botoiak izkutatu/erakutsi
                btnEditatuPerfila.setVisibility(View.INVISIBLE);
                btnGordePerfila.setVisibility(View.VISIBLE);

                // Editagarrariak bihurtu
                izenaEnpresa.setEnabled(true);
                emailEnpresa.setEnabled(true);
                telEnpresa.setEnabled(true);
                Log.i(TAG, currentUser.getEmail());
            }
        });

        // Gorde botoia
        btnGordePerfila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!izenaEnpresa.getText().toString().isEmpty() & !emailEnpresa.getText().toString().isEmpty() & !telEnpresa.getText().toString().isEmpty()){
                    // Botoiak izkutatu/erakutsi
                    btnEditatuPerfila.setVisibility(View.VISIBLE);
                    btnGordePerfila.setVisibility(View.INVISIBLE);
                    message_update.show();

                    // erabiltzailea sortu eta datu baseko informazioa eguneratu
                    Erabiltzailea e = new Erabiltzailea(emailEnpresa.getText().toString(), nanEnpresa.getText().toString(), izenaEnpresa.getText().toString(), telEnpresa.getText().toString(), nifEnpresa.getText().toString(), true);
                    db.collection("Erabiltzaileak").document(emailEnpresa.getText().toString()).set(e);

                    // Editagarritasuna kendu
                    izenaEnpresa.setEnabled(false);
                    emailEnpresa.setEnabled(false);
                    telEnpresa.setEnabled(false);
                }else{
                    error_field.show();
                    Log.e(TAG, "ERROR");
                }
            }
        });

        // Sesioa ixteko botoia
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Perfila.this, Login.class);
                startActivity(intent);
            }
        });

        Button btnKontuaEzabatu = findViewById(R.id.btnKontuaEzabatu);
        btnKontuaEzabatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Behean egindako metodoari deitzen dio ezabatu kontua botoia klikatzen duzunean
                mostrarDialogoDeConfirmacion();
            }
        });
    }

    private void mostrarDialogoDeConfirmacion() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.ezabatuMezua));

        builder.setPositiveButton(getString(R.string.baiKontuaEzabatu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hemen kontua ezabatuko da
                currentUser.delete();
                // Behin kontua ezabatu denean Login pantailara eramango zaizu
                Intent intent = new Intent(Perfila.this, Login.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(getString(R.string.kantzelatu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ematen badiozu kantzelatu botoiari ez da ezer gertatuko eta mezua desagertuko da
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}