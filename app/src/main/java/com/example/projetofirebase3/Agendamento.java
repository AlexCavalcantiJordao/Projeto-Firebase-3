package com.example.projetofirebase3;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofirebase3.databinding.ActivityAgendamentoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Agendamento extends AppCompatActivity {
    private ActivityAgendamentoBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private String data = "";
    private String hora = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgendamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nome = getIntent().getExtras().getString("nome").toString();
        DatePicker datePicker = binding.datePicket;
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dia = Integer.toString(dayOfMonth);
                String mes;
                if (dayOfMonth < 10) {
                    dia = "0" + dayOfMonth;
                }
                if (monthOfYear < 9) {
                    mes = "0" + (monthOfYear + 1);
                } else {
                    mes = Integer.toString(monthOfYear + 1);
                }
                data = dia + "/" + mes + "/" + year;
            }
        });
    }
}

