
package com.example.projetofirebase3;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetofirebase3.databinding.ActivityAgendamentoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Agendamento extends AppCompatActivity {

    private Button btAgendar;
    private ActivityAgendamentoBinding binding;
    private String data = "";
    private String hora = "";
    private String Agenda;
    private final Calendar calendar = Calendar.getInstance();

    String usuarioID;

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
        binding.btAgendar.setOnClickListener(view -> {
            boolean tecnico1 = binding.tecnico1.isChecked();
            boolean tecnico2 = binding.tecnico2.isChecked();
            boolean tecnico3 = binding.tecnico3.isChecked();
            if (hora.isEmpty()) {
                mensagem(view, "Preencha o horário", "#FF0000");
            } else if (hora.compareTo("8:00") < 0 || hora.compareTo("17:00") > 0) {
                mensagem(view, "Suporte Técnico não está em funcionamento - horário é das 08 Horas : 00 minutos ás 17 Horas : 00 minutos.", "#FF0000");
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

    private void salvarAgendamento(View view, String tecnico1, String tecnico2, String tecnico3, String agenda) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String Agenda = btAgendar.getText().toString();
        Map<String, Object> usuarioID = new HashMap<>();
        usuarioID.put("nome", "Nome do Cliente");
        usuarioID.put("email", "email@cliente.com");
        usuarioID.put("técnico1", tecnico1);
        usuarioID.put("técnico2", tecnico2);
        usuarioID.put("técnico3", tecnico3);
        usuarioID.put("agenda", agenda);
        usuarioID.put("data", new Timestamp(new Date()));

        db.collection("Agendamento").document(agenda).set(usuarioID).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mensagem(view, "Agendamento realizado com sucesso !", "#FFFAF0");
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(e -> {
            mensagem(view, "Erro no servidor !", "#FF0000");
            Log.w(TAG, "Error writing document", e);
        });
    }
}
