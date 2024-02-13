package com.example.projetofirebase3.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofirebase3.R;
import com.example.projetofirebase3.databinding.ActivityAgendamentoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Agendamento extends AppCompatActivity {
    private Button bt_agenda, bt_seguinte;

    private TextView text_data, text_hora, text_nomeTecnico1, text_nomeTecnico2, text_nomeTecnico3, text_dia, text_mes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("agenda");
    private Calendar calendar = Calendar.getInstance();
    private Integer data = Integer.valueOf("");
    private Integer hora = Integer.valueOf("");
    private Integer year = Integer.valueOf("");
    private Integer monthOfYear = Integer.valueOf("");
    private Integer dayOfMonth = Integer.valueOf("");
    String[] mensagens = {"Preencha todos os campos.", "Foi agendado com sucesso."};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        IniciarComponentes();

        ActivityAgendamentoBinding binding = ActivityAgendamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nome = getIntent().getExtras().getString("nome", "").toString();
        DatePicker datePicker = binding.datePicket;
        Calendar calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String dia = dayOfMonth < 10 ? "0" + dayOfMonth : Integer.toString(dayOfMonth);
                    String mes = monthOfYear < 9 ? "0" + (monthOfYear + 1) : Integer.toString(monthOfYear + 1);
                    String data = dia + " / " + mes + " / " + year;
                }
            });
        }

        TimePicker timePicker = binding.timePicker;
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String minuto = minute < 10 ? "0" + minute : Integer.toString(minute);
                String hora = hourOfDay + ":" + minuto;
            }
        });
        timePicker.setIs24HourView(true);

        bt_seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                String data = text_data.getText().toString();
                String hora = text_hora.getText().toString();
                String nomeTecnico1 = text_nomeTecnico1.getText().toString();
                String nomeTecnico2 = text_nomeTecnico2.getText().toString();
                String nomeTecnico3 = text_nomeTecnico3.getText().toString();

                if (data.isEmpty() || hora.isEmpty() || nomeTecnico1.isEmpty() || nomeTecnico2.isEmpty() || nomeTecnico3.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    CadastrarUsuario(v);
                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }

            }
        });
    }

    protected void onStart() {
        super.onStart();

        String agenda = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // FirebaseUser currentUser = mAuth.getCurrentUser();

        DocumentReference documentReference = db.collection("Usuários").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    text_nomeTecnico1.setText(documentSnapshot.getString("nome"));
                    text_nomeTecnico1.setText(agenda);
                    text_nomeTecnico2.setText(documentSnapshot.getString("nome"));
                    text_nomeTecnico2.setText(agenda);
                    text_nomeTecnico3.setText(documentSnapshot.getString("nome"));
                    text_nomeTecnico3.setText(agenda);
                }
            }
        });
    }

    // Aqui vai salvar os dados do usuário...
    private void CadastrarUsuario(View v) {

        String data = text_data.getText().toString();
        String hora = text_hora.getText().toString();
        String nomeTecnico1 = text_nomeTecnico1.getText().toString();
        String nomeTecnico2 = text_nomeTecnico2.getText().toString();
        String nomeTecnico3 = text_nomeTecnico3.getText().toString();

        System.out.println("Nome dos Técnicos: " + data + hora + nomeTecnico1 + nomeTecnico2 + nomeTecnico3);

        // Criar um ID único para a pessoa no Firebase
        String usuarioID = databaseReference.push().getKey();
        Agenda agenda = new Agenda(data, hora, nomeTecnico1, nomeTecnico2, nomeTecnico3);

        databaseReference.child(usuarioID).setValue(agenda).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("chamada", "Deus me Ajuda" + agenda.getTecnico1()); // 1
                Log.d("chamada", "Deus me Ajuda" + agenda.getTecnico2()); // 2
                Log.d("chamada", "Deus me Ajuda" + agenda.getTecnico3()); // 3

                if (task.isSuccessful()) {
                    Log.d("entrou", "entrou " + task.isSuccessful());
                    SalvarDadosUsuario();

                } else {
                    String erro;
                    try {
                        throw task.getException();

                    } catch (Exception e) {
                        erro = "Erro ao não preencher as todas as tabelas.";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    // Aqui que vai salvar os dados do usuário que vai relatar os problemas do computador por sistema "Aplicativo"....
    private void SalvarDadosUsuario() {
        String data = text_data.getText().toString();
        String hora = text_hora.getText().toString();
        String dia = text_dia.getText().toString();
        String mes = text_mes.getText().toString();
        String tecnico1 = text_nomeTecnico1.getText().toString();
        String tecnico2 = text_nomeTecnico2.getText().toString();
        String tecnico3 = text_nomeTecnico3.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("data, hora, dia, mes", tecnico1 + tecnico2 + tecnico3);

        DocumentReference documentReference = db.collection("Usuários").document(usuarioID);

        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void oVoid) {
                        Log.d("db", "Sucesso ao salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("db_error", "Erro ao salvar os dados" + e.toString());

                    }
                });
    }

    private void IniciarComponentes() {

        text_data = findViewById(R.id.text_data);
        text_hora = findViewById(R.id.text_hora);
        bt_agenda = findViewById(R.id.bt_agenda);
        bt_seguinte = findViewById(R.id.bt_seguinte);


    }
}
