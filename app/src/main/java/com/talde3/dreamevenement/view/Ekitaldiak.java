package com.talde3.dreamevenement.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.talde3.dreamevenement.R;
import com.talde3.dreamevenement.model.Ekitaldia;
import com.talde3.dreamevenement.model.Erreserba;

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
        // Datu baseko istantzia
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // authentificator instantzia eta logeatutako erabiltzailearen informazioa bildu
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Button btnHurrengoOrria = findViewById(R.id.btnHurrengoOrria);

        Intent intent = getIntent();

        ImageView imgEkitaldia = findViewById(R.id.imgEkitaldia);
        TextView txtGonbidatuak = findViewById(R.id.txtGonbidatuak);
        TextView txtPrezioak = findViewById(R.id.txtPrezioak);
        TextView txtIzena = findViewById(R.id.txtIzena);
        TextView txtDeskribapena = findViewById(R.id.txtDeskribapena);
        DatePicker simpleDatePicker = findViewById(R.id.simpleDatePicker);
        NumberPicker numberPickerGonbidatuak = findViewById(R.id.numberPickerGonbidatuak);
        NumberPicker numberPickerArgazkilariOrduak = findViewById(R.id.numberPickerArgazkilariOrduak);
        CheckBox checkBoxApainketa = findViewById(R.id.checkBoxApainketa);

        Ekitaldia ekitaldi = null;

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("eskontza_id")) {
                    ekitaldi = (Ekitaldia) bundle.getSerializable("eskontza_id");
                } else if (bundle.containsKey("urtebetetzea_id")) {
                    ekitaldi = (Ekitaldia) bundle.getSerializable("urtebetetzea_id");
                }else if (bundle.containsKey("jana_id")) {
                    ekitaldi = (Ekitaldia) bundle.getSerializable("jana_id");
                }else if (bundle.containsKey("afaria_id")) {
                    ekitaldi = (Ekitaldia) bundle.getSerializable("afaria_id");
                }else if (bundle.containsKey("jaunartzeaBataioa_id")) {
                    ekitaldi = (Ekitaldia) bundle.getSerializable("jaunartzeaBataioa_id");
                }
                if(ekitaldi != null){
                    displayEventInfo(ekitaldi, imgEkitaldia, txtGonbidatuak, txtPrezioak, txtIzena, txtDeskribapena, numberPickerGonbidatuak, numberPickerArgazkilariOrduak, checkBoxApainketa, simpleDatePicker);
                    Ekitaldia finalE = ekitaldi;
                    btnHurrengoOrria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            erreserbaEgin(finalE, numberPickerGonbidatuak, numberPickerArgazkilariOrduak, checkBoxApainketa, simpleDatePicker);
                            Calendar egutegia = Calendar.getInstance();
                            Date gaur = egutegia.getTime();
                            SimpleDateFormat DataFormatua = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault());
                            String DataFormateatuta = DataFormatua.format(gaur);

                            db.collection("Erreserbak").document(DataFormateatuta + "_" + currentUser.getEmail()).set(erreserba);
                            Toast.makeText(Ekitaldiak.this, "La reserba se ha guardado",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(Ekitaldiak.this, Etxea.class);
                            startActivity(intent2);
                        }
                    });
                }
            }
        }
    }
    private void displayEventInfo(Ekitaldia event, ImageView imgEkitaldia, TextView txtGonbidatuak, TextView txtPrezioak, TextView txtIzena, TextView txtDeskribapena, NumberPicker numberPickerGonbidatuak, NumberPicker numberPickerArgazkilariOrduak, CheckBox checkBoxApainketa, DatePicker simpleDatePicker) {



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

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            simpleDatePicker.init(year, month, day, null);

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

    private void erreserbaEgin(Ekitaldia event, NumberPicker numberPickerGonbidatuak, NumberPicker numberPickerArgazkilariOrduak, CheckBox checkBoxApainketa, DatePicker simpleDatePicker){
        double apainketa = 0;

        if(checkBoxApainketa.isChecked()) {
            apainketa = event.getPrezioak().get(1).doubleValue();
        }
        int urtea = simpleDatePicker.getYear();
        int hila = simpleDatePicker.getMonth() +1;
        int eguna = simpleDatePicker.getDayOfMonth();

        Log.i(TAG, urtea + " " + hila + " " + eguna);

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
