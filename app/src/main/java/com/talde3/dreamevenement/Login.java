package com.talde3.dreamevenement;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, pasahitza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtErregistroa = findViewById(R.id.txtErregistroaLogin);
        TextView txtAnonymousLogin = findViewById(R.id.txtAnonymousLogin);

        email = findViewById(R.id.etEmailLogin);
        pasahitza = findViewById(R.id.etPasahitzaLogin);

        mAuth = FirebaseAuth.getInstance();

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
                                        Intent intent = new Intent(Login.this, Etxea.class);
                                        startActivity(intent);
                                        gordeSharedPreferences();
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
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInAnonymously:success");
                                    Intent intent = new Intent(Login.this, Etxea.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void kargatuSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        String user = preferences.getString("user", "");
        email.setText(user);
    }

    private void gordeSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        String user = email.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", user);

        email.setText(user);

        editor.commit();
    }
}