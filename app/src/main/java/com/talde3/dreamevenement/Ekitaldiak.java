package com.talde3.dreamevenement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Ekitaldiak extends AppCompatActivity {
    private Erreserba erreserba;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekitaldiak);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Button btnHurrengoOrria = findViewById(R.id.btnHurrengoOrria);

        Intent intent = getIntent();

        ImageView imgEkitaldia = findViewById(R.id.imgEkitaldia);
        TextView txtGonbidatuak = findViewById(R.id.txtGonbidatuak);
        TextView txtPrezioak = findViewById(R.id.txtPrezioak);
        TextView txtIzena = findViewById(R.id.txtIzena);
        TextView txtDeskribapena = findViewById(R.id.txtDeskribapena);
        NumberPicker numberPickerGonbidatuak = findViewById(R.id.numberPickerGonbidatuak);
        NumberPicker numberPickerArgazkilariOrduak = findViewById(R.id.numberPickerArgazkilariOrduak);
        CheckBox checkBoxApainketa = findViewById(R.id.checkBoxApainketa);

        Ekitaldia e = null;

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("eskontza_id")) {
                    e = (Ekitaldia) bundle.getSerializable("eskontza_id");
                } else if (bundle.containsKey("urtebetetzea_id")) {
                    e = (Ekitaldia) bundle.getSerializable("urtebetetzea_id");
                }else if (bundle.containsKey("jana_id")) {
                    e = (Ekitaldia) bundle.getSerializable("jana_id");
                }else if (bundle.containsKey("afaria_id")) {
                    e = (Ekitaldia) bundle.getSerializable("afaria_id");
                }else if (bundle.containsKey("jaunartzeaBataioa_id")) {
                    e = (Ekitaldia) bundle.getSerializable("jaunartzeaBataioa_id");
                }
                if(e != null){
                    displayEventInfo(e, imgEkitaldia, txtGonbidatuak, txtPrezioak, txtIzena, txtDeskribapena, numberPickerGonbidatuak, numberPickerArgazkilariOrduak, checkBoxApainketa);
                    Ekitaldia finalE = e;
                    btnHurrengoOrria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            erreserbaEgin(finalE, numberPickerGonbidatuak, numberPickerArgazkilariOrduak, checkBoxApainketa);
                            Calendar egutegia = Calendar.getInstance();
                            Date gaur = egutegia.getTime();
                            SimpleDateFormat DataFormatua = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault());
                            String DataFormateatuta = DataFormatua.format(gaur);

                            db.collection("Erreserbak").document(DataFormateatuta + "_" + currentUser.getEmail()).set(erreserba);
                            Intent intent2 = new Intent(Ekitaldiak.this, Etxea.class);
                            startActivity(intent2);
                        }
                    });
                }
            }
        }
    }
    private void displayEventInfo(Ekitaldia event, ImageView imgEkitaldia, TextView txtGonbidatuak, TextView txtPrezioak, TextView txtIzena, TextView txtDeskribapena, NumberPicker numberPickerGonbidatuak, NumberPicker numberPickerArgazkilariOrduak, CheckBox checkBoxApainketa) {



        if (event != null) {
            String argazkia = event.getArgazkia();
            int idImagen = getResources().getIdentifier(argazkia, "drawable", getPackageName());
            imgEkitaldia.setImageResource(idImagen);

            String gonbidatuakStr = formatGonbidatuak(event.getGonbidatuak());
            txtGonbidatuak.setText(gonbidatuakStr);

            String prezioakStr = formatPrezioa(event.getPrezioak());
            txtPrezioak.setText(prezioakStr);

            txtIzena.setText(event.getIzena());
            txtDeskribapena.setText(event.getDeskribapena());


            ArrayList<Number> gonbidatuak = event.getGonbidatuak();
            int minInvitados = gonbidatuMin(gonbidatuak);
            int maxInvitados = gonbidatuMax(gonbidatuak);

            numberPickerGonbidatuak.setMinValue(minInvitados);
            numberPickerGonbidatuak.setMaxValue(maxInvitados);
            numberPickerGonbidatuak.setValue(minInvitados);

            numberPickerArgazkilariOrduak.setMinValue(2);
            numberPickerArgazkilariOrduak.setMaxValue(10);
            numberPickerArgazkilariOrduak.setValue(2);
        }
    }

    private void erreserbaEgin(Ekitaldia event, NumberPicker numberPickerGonbidatuak, NumberPicker numberPickerArgazkilariOrduak, CheckBox checkBoxApainketa){
        double apainketa = 0;

        if(checkBoxApainketa.isChecked()) {
            apainketa = event.getPrezioak().get(1).doubleValue();
        }

        double totala = prezioaKalkulatu(numberPickerArgazkilariOrduak.getValue(), numberPickerGonbidatuak.getValue(), event.getPrezioak().get(0).doubleValue(), apainketa, event.getPrezioak().get(2).doubleValue(), event.getPrezioak().get(3).doubleValue());
        erreserba = new Erreserba(numberPickerArgazkilariOrduak.getValue(), numberPickerGonbidatuak.getValue(), event.getPrezioak().get(0).doubleValue(), apainketa, event.getPrezioak().get(2).doubleValue(), event.getPrezioak().get(3).doubleValue(), totala);
    }

    private String formatGonbidatuak(ArrayList<Number> gonbidatuak) {

        int maxGonbidatuak = gonbidatuak.get(0).intValue();
        int minGonbidatuak = gonbidatuak.get(1).intValue();

        return "• Invitados míninos: " + minGonbidatuak + "\n• Invitados máximos: " + maxGonbidatuak;
    }

    private String formatPrezioa(ArrayList<Number> prezioak) {

        int alokairua = prezioak.get(0).intValue();
        int apainketa = prezioak.get(1).intValue();
        int argazkilaria = prezioak.get(2).intValue();
        int menua = prezioak.get(3).intValue();

        return "• Alquiler: " + alokairua + "€ " + "\n• Decoración: " + apainketa + "€" +
                "\n• Fotógrafo: " + argazkilaria + "€/h " + "\n• Menú: " + menua + "€/pers.";
    }

    private int gonbidatuMin(ArrayList<Number> gonbidatuak) {
        int minGonbidatuak = gonbidatuak.get(1).intValue();
        return minGonbidatuak;
    }

    private int gonbidatuMax(ArrayList<Number> gonbidatuak) {
        int maxGonbidatuak = gonbidatuak.get(0).intValue();
        return maxGonbidatuak;
    }

    private double prezioaKalkulatu(int argazki_ordu, int gonbidatuak, double alokairua_tarifa, double apainketa_tarifa, double argazkilaria_tarifa, double menua_tarifa) {
        double totala = 0;

        totala = argazki_ordu * argazkilaria_tarifa + gonbidatuak * menua_tarifa + alokairua_tarifa + apainketa_tarifa;

        return totala;
    }

}
