package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.project.R.layout.activity_main;

public class Login extends Activity {
//    Firebase é uma plataforma Web que nos permite salvar os dados de nossas aplicações na base de dados NoSQL provida por eles.
    //    criando o firebase

    private FirebaseAuth Auth;
    private EditText Usuario;
    private EditText Senha;
     private Button Cadastrar, Entrar;
    private TextView txtResetSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
//    button registrando um novo cadastro
        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });

        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Usuario.getText().toString().trim();
                String senha = Senha.getText().toString().trim();
                login(email, senha);
            }
        });
        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.project.Login.this,ResetSenha.class);
                startActivity(i);
            }
        });
    }

    private void login(String email, String senha) {
//        chamando o objeto aut para autenticar
        Auth.signInWithEmailAndPassword(email, senha)
//                passando a activity onde estou
                .addOnCompleteListener(com.example.project.Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            para onde vamos dirigir ao final da configuração
                            Intent i = new Intent(com.example.project.Login.this, Perfil.class);
                            startActivity(i);
                        } else {
                            alert("email ou senha errados");
                        }
                    }
                });
    }

    private void alert(String email_ou_senha_errados) {
        Toast.makeText(com.example.project.Login.this,email_ou_senha_errados,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        @Override
        protected void onStart() {
            super.onStart();
            Auth= Conexao.getFirebaseAuth();

        }
    }
}
