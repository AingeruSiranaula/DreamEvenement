package com.talde3.dreamevenement;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Erregistroa extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erregistroa);

        LinearLayout layoutEnpresa = findViewById(R.id.layoutEnpresa);
        LinearLayout layoutPertsona = findViewById(R.id.layoutPertsona);
        RadioGroup radioGroup = findViewById(R.id.rgroupBezeroMotaErregistroa);
        RadioButton rbtnEnpresa = findViewById(R.id.rbtnEnpresaErregistroa);

        TextView txtEnpresaTerminoak = findViewById(R.id.txtEnpresaTerminoak);

        Button btnErregistroa = findViewById(R.id.btnErregistroa);

        // Enpresa datuak
        EditText nifEnpresa = findViewById(R.id.edtNifEnpresaErregistro);
        EditText izenaEnpresa = findViewById(R.id.edtEnpresaIzenaErregistro);
        EditText nanEnpresa = findViewById(R.id.edtNanEnpresaErregistro);
        EditText emailEnpresa = findViewById(R.id.edtEmailEnpresaErregistro);
        EditText telEnpresa = findViewById(R.id.edtTelEnpresaErregistro);
        EditText pasEnpresa = findViewById(R.id.edtPasahitzaEnpresaErregistro);
        EditText pasRepEnpresa = findViewById(R.id.edtPasahitzaRepEnpresaErregistro);

        // Pertsona datuak
        EditText izenaPertsona = findViewById(R.id.edtPertsonaIzenaErregistro);
        EditText nanPertsona = findViewById(R.id.edtNanPertsonaErregistro);
        EditText emailPertsona = findViewById(R.id.edtEmailPertsonaErregistro);
        EditText telPertsona = findViewById(R.id.edtTelPertsonaErregistro);
        EditText pasPertsona = findViewById(R.id.edtPasahitzaPertsonaErregistro);
        EditText pasRepPertsona = findViewById(R.id.edtPasahitzaRepPertsonaErregistro);

        btnErregistroa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;

                Toast error_message = Toast.makeText(Erregistroa.this, getString(R.string.errorField), duration);
                Toast error_message_contra = Toast.makeText(Erregistroa.this, getString(R.string.errorPass), duration);

                if(!nifEnpresa.getText().toString().isEmpty() & !izenaEnpresa.getText().toString().isEmpty() & !nanEnpresa.getText().toString().isEmpty() & !emailEnpresa.getText().toString().isEmpty() & !telEnpresa.getText().toString().isEmpty() & !pasEnpresa.getText().toString().isEmpty() & !pasRepEnpresa.getText().toString().isEmpty() | !izenaPertsona.getText().toString().isEmpty() & !nanPertsona.getText().toString().isEmpty() & !emailPertsona.getText().toString().isEmpty() & !telPertsona.getText().toString().isEmpty() & !pasPertsona.getText().toString().isEmpty() & !pasRepPertsona.getText().toString().isEmpty()){
                    if(pasEnpresa.getText().toString().equals(pasRepEnpresa.getText().toString()) | pasPertsona.getText().toString().equals(pasRepPertsona.getText().toString())){
                        mAuth = FirebaseAuth.getInstance();

                        boolean egoera = rbtnEnpresa.isChecked();
                        String email;
                        String pasahitza;
                        if(egoera){
                            email = emailEnpresa.getText().toString();
                            pasahitza = pasEnpresa.getText().toString();
                        }else{
                            email = emailPertsona.getText().toString();
                            pasahitza = pasPertsona.getText().toString();
                        }

                        mAuth.createUserWithEmailAndPassword(email,pasahitza)
                                .addOnCompleteListener(Erregistroa.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(Erregistroa.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                }else{
                    error_message.show();
                    Log.e(TAG, "ERROR");
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtnEnpresaErregistroa){
                    layoutEnpresa.setVisibility(View.VISIBLE);
                    layoutPertsona.setVisibility(View.INVISIBLE);
                } else {
                    layoutEnpresa.setVisibility(View.INVISIBLE);
                    layoutPertsona.setVisibility(View.VISIBLE);
                }
            }
        });

        txtEnpresaTerminoak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Erregistroa.this, TerminoKondizioak.class);
                startActivity(intent);
            }
        });
    }
}