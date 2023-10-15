package com.talde3.dreamevenement;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

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
        Button btnEzeztatuPerfila = findViewById(R.id.btnEzeztatuPerfila);
        Button btnGordePerfila = findViewById(R.id.btnGordePerfila);
        Button btnEditatuPerfila = findViewById(R.id.btnEditatuPerfila);

        EditText nifEnpresa = findViewById(R.id.edtNifEnpresaPerfila);
        EditText izenaEnpresa = findViewById(R.id.edtEnpresaIzenaPerfila);
        EditText pasEnpresa = findViewById(R.id.edtPasahitzaEnpresaPerfila);
        EditText emailEnpresa = findViewById(R.id.edtEmailEnpresaPerfila);
        EditText telEnpresa = findViewById(R.id.edtTelEnpresaPerfila);
        EditText nanEnpresa = findViewById(R.id.edtNanEnpresaPerfila);

        //  Aldagai finalak
        final String[] izena = new String[1];
        final String[] email = new String[1];
        final String[] tel = new String[1];
        final String[] pas = new String[1];

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
                pasEnpresa.setText(e.getPasahitza());
                nanEnpresa.setText(e.getNan());

                izena[0] = e.getNif();
                email[0] = e.getEmail();
                tel[0] = e.getTelefonoa();
                pas[0] = e.getPasahitza();
            }
        });

        // Edizio botoia
        btnEditatuPerfila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Botoiak izkutatu/erakutsi
                btnEditatuPerfila.setVisibility(View.INVISIBLE);
                btnGordePerfila.setVisibility(View.VISIBLE);
                btnEzeztatuPerfila.setVisibility(View.VISIBLE);

                // Editagarrariak bihurtu
                izenaEnpresa.setEnabled(true);
                emailEnpresa.setEnabled(true);
                telEnpresa.setEnabled(true);
                pasEnpresa.setEnabled(true);
                //Log.i(TAG, currentUser.getEmail());
            }
        });

        // Gorde botoia
        btnGordePerfila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!izenaEnpresa.getText().toString().isEmpty() & !emailEnpresa.getText().toString().isEmpty() & !telEnpresa.getText().toString().isEmpty() & !pasEnpresa.getText().toString().isEmpty()){
                    // Botoiak izkutatu/erakutsi
                    btnEditatuPerfila.setVisibility(View.VISIBLE);
                    btnGordePerfila.setVisibility(View.INVISIBLE);
                    btnEzeztatuPerfila.setVisibility(View.INVISIBLE);
                    message_update.show();

                    // erabiltzailea sortu eta datu baseko informazioa eguneratu
                    Erabiltzailea e = new Erabiltzailea(emailEnpresa.getText().toString(), pasEnpresa.getText().toString(), nanEnpresa.getText().toString(), izenaEnpresa.getText().toString(), telEnpresa.getText().toString(), nifEnpresa.getText().toString(), true);
                    db.collection("Erabiltzaileak").document(emailEnpresa.getText().toString()).set(e);

                    // Editagarritasuna kendu
                    izenaEnpresa.setEnabled(false);
                    emailEnpresa.setEnabled(false);
                    telEnpresa.setEnabled(false);
                    pasEnpresa.setEnabled(false);
                }else{
                    error_field.show();
                    Log.e(TAG, "ERROR");
                }
            }
        });

        // Ezeztatu botoia
        btnEzeztatuPerfila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Botoiak izkutatu/erakutsi
                btnEditatuPerfila.setVisibility(View.VISIBLE);
                btnGordePerfila.setVisibility(View.INVISIBLE);
                btnEzeztatuPerfila.setVisibility(View.INVISIBLE);

                // Egindako aldaketak ezeztatu
                izenaEnpresa.setText(izena[0]);
                emailEnpresa.setText(email[0]);
                telEnpresa.setText(tel[0]);
                pasEnpresa.setText(pas[0]);

                // Editagarritasuna kendu
                izenaEnpresa.setEnabled(false);
                emailEnpresa.setEnabled(false);
                telEnpresa.setEnabled(false);
                pasEnpresa.setEnabled(false);
            }
        });
    }
}