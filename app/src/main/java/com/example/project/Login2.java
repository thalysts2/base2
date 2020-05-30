package com.example.projeto_semestre2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button btnLogar, btnNovo;
    private TextView txtResetSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
//    button registrando um novo cadastro
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                login(email, senha);
            }
        });
        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,ResetSenha.class);
                startActivity(i);
            }
        });
    }

    private void login(String email, String senha) {
//        chamando o objeto aut para autenticar
        auth.signInWithEmailAndPassword(email, senha)
//                passando a activity onde estou
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            para onde vamos dirigir ao final da configuração
                            Intent i = new Intent(Login.this, Perfil.class);
                            startActivity(i);
                        } else {
                            alert("email ou senha errados");
                        }
                    }
                });
    }

    private void alert(String email_ou_senha_errados) {
        Toast.makeText(Login.this,email_ou_senha_errados,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        @Override
        protected void onStart() {
            super.onStart();
            auth= Conexao.getFirebaseAuth();

        }
    }
}

