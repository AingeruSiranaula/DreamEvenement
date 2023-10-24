package com.talde3.dreamevenement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class Ekitaldiak extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekitaldiak);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("eskontza_id")) {
                    Ekitaldia e = (Ekitaldia) bundle.getSerializable("eskontza_id");
                    if (e != null) {
                        displayEventInfo(e);
                    }
                } else if (bundle.containsKey("urtebetetzea_id")) {
                    Ekitaldia u = (Ekitaldia) bundle.getSerializable("urtebetetzea_id");
                    if (u != null) {
                        displayEventInfo(u);
                    }
                }else if (bundle.containsKey("jana_id")) {
                    Ekitaldia j = (Ekitaldia) bundle.getSerializable("jana_id");
                    if (j != null) {
                        displayEventInfo(j);
                    }
                }else if (bundle.containsKey("afaria_id")) {
                    Ekitaldia a = (Ekitaldia) bundle.getSerializable("afaria_id");
                    if (a != null) {
                        displayEventInfo(a);
                    }
                }else if (bundle.containsKey("jaunartzeaBataioa_id")) {
                Ekitaldia a = (Ekitaldia) bundle.getSerializable("jaunartzeaBataioa_id");
                if (a != null) {
                    displayEventInfo(a);
                }
            }
            }
        }
    }
    private void displayEventInfo(Ekitaldia event) {
        ImageView imgEkitaldia = findViewById(R.id.imgEkitaldia);
        TextView txtGonbidatuak = findViewById(R.id.txtGonbidatuak);
        TextView txtPrezioak = findViewById(R.id.txtPrezioak);
        TextView txtIzena = findViewById(R.id.txtIzena);
        TextView txtDeskribapena = findViewById(R.id.txtDeskribapena);
        NumberPicker numberPickerGonbidatuak = findViewById(R.id.numberPickerGonbidatuak);
        NumberPicker numberPickerArgazkilariOrduak = findViewById(R.id.numberPickerArgazkilariOrduak);


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

    private String formatGonbidatuak(ArrayList<Number> gonbidatuak) {

        int maxGonbidatuak = gonbidatuak.get(0).intValue();
        int minGonbidatuak = gonbidatuak.get(1).intValue();

        return "• Invitados mínimnos: " + minGonbidatuak + "\n• Invitados máximos: " + maxGonbidatuak;
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

}
