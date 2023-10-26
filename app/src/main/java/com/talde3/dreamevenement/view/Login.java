package com.talde3.dreamevenement.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.talde3.dreamevenement.R;
import com.talde3.dreamevenement.model.Erabiltzailea;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email, pasahitza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Authentification instantzia eta logeatutako erabiltzailearen informazioa lortu
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtErregistroa = findViewById(R.id.txtErregistroaLogin);
        TextView txtAnonymousLogin = findViewById(R.id.txtAnonymousLogin);

        email = findViewById(R.id.etEmailLogin);
        pasahitza = findViewById(R.id.etPasahitzaLogin);
         // Shared preferences kargatu
        kargatuSharedPreferences();

        // Logeatze kasuan
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;

                // Errore mezuak
                Toast error_message = Toast.makeText(Login.this, getString(R.string.errorField), duration);

                if(!email.getText().toString().isEmpty() & !pasahitza.getText().toString().isEmpty()){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pasahitza.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        DocumentReference docRef = db.collection("Erabiltzaileak").document(currentUser.getEmail());
                                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                // Datuak erabiltzaile objetu bihurtu
                                                Erabiltzailea erabil = documentSnapshot.toObject(Erabiltzailea.class);

                                                Intent intent = new Intent(Login.this, Etxea.class);
                                                intent.putExtra("erabiltzailea_mota", erabil);
                                                startActivity(intent);
                                                gordeSharedPreferences();
                                            }
                                        });
                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Contraseña o usuario incorrectos",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    error_message.show();
                    Log.e(TAG, "ERROR");
                }
            }
        });

        // Erregistro kasuan
        txtErregistroa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Erregistroa.class);
                startActivity(intent);
            }
        });

        // Login anonimoa
        txtAnonymousLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInAnonymously()
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Anonimo bezala loga ondo
                                    Log.d(TAG, "signInAnonymously:success");
                                    Intent intent = new Intent(Login.this, Etxea.class);
                                    startActivity(intent);
                                } else {
                                    // Anonimo bezala loga errore
                                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    // Shared preferences kargatzeko metodoa
    private void kargatuSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        String user = preferences.getString("user", "");
        email.setText(user);
    }

    // Shared preferences gordetzeko metodoa
    private void gordeSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        String user = email.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", user);

        email.setText(user);

        editor.commit();
    }
}