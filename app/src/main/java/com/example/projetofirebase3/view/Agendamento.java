package com.example.projetofirebase3.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofirebase3.R;
import com.example.projetofirebase3.databinding.ActivityAgendamentoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Agendamento extends AppCompatActivity {

    private Button bt_agenda;
    private ActivityAgendamentoBinding binding;
    private String data = "";
    private String hora = "";
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        binding = ActivityAgendamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nome = getIntent().getExtras() != null ? getIntent().getExtras().getString("nome") : "";
        DatePicker datePicker = binding.datePicket;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dia = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String mes = monthOfYear < 9 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                data = dia + " / " + mes + " / " + year;
            });
        }
        TimePicker timePicker = binding.timePicker;
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            String minuto = minute < 10 ? "0" + minute : String.valueOf(minute);
            hora = hourOfDay + ":" + minuto;
        });
        timePicker.setIs24HourView(true);
        binding.btAgenda.setOnClickListener(view -> {
            boolean tecnico1 = binding.tecnico1.isChecked();
            boolean tecnico2 = binding.tecnico2.isChecked();
            boolean tecnico3 = binding.tecnico3.isChecked();
            if (hora.isEmpty()) {
                mensagem(view, "Preencha o horário", "#FF0000");
            } else if (hora.compareTo("8:00") < 0 || hora.compareTo("17:00") > 0) {
                mensagem(view, "Suporte Técnico não está em funcionamento - horário é 08 Horas : 00 minutos ás 17 Horas : 00 minutos", "#FF0000");
            } else if (data.isEmpty()) {
                mensagem(view, "Coloque uma data", "#FF0000");
            } else if (tecnico1 && !data.isEmpty() && !hora.isEmpty()) {
                salvarAgendamento(view, nome, "Alex Fonseca", data, hora);
            } else if (tecnico2 && !data.isEmpty() && !hora.isEmpty()) {
                salvarAgendamento(view, nome, "Pedro Paulo", data, hora);
            } else if (tecnico3 && !data.isEmpty() && !hora.isEmpty()) {
                salvarAgendamento(view, nome, "Lilian Cavalcanti", data, hora);
            } else {
                mensagem(view, "Escolha um técnico", "#FF0000");
            }
        });
    }

    private void mensagem(View view, String mensagem, String cor) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.parseColor(cor));
        snackbar.setTextColor(Color.parseColor("#FFFFFF"));
        snackbar.show();
    }

    private void salvarAgendamento(View view, String cliente, String agenda, String data, String hora) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("cliente", cliente);
        dadosUsuario.put("Técnico", agenda);
        dadosUsuario.put("data", data);
        dadosUsuario.put("hora", hora);
        db.collection("Agendamento").document(cliente).set(dadosUsuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mensagem(view, "Agendamento realizado com sucesso !", "#FF03DAC5");
            }
        }).addOnFailureListener(e -> {
            mensagem(view, "Erro no servidor !", "#FF0000");
        });
    }
}