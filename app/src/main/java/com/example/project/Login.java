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

import static com.example.project.R.layout.activity_main;

public class Login extends Activity {
//    Firebase é uma plataforma Web que nos permite salvar os dados de nossas aplicações na base de dados NoSQL provida por eles.
    //    criando o firebase

    private FirebaseAuth Auth;
    private EditText Usuario;
    private EditText Senha;
    private Button btnCadastrar, btnEntrar;
    private TextView txtRsenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        inicializarComponentes();
        eventoClicks();
    }

    private void inicializarComponentes() {
    }


    private void eventoClicks() {
//    button registrando um novo cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Usuario.getText().toString().trim();
                String senha = Senha.getText().toString().trim();
                login(email, senha);
            }
        });
        txtRsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ResetSenha.class);
                startActivity(i);
            }
        });
    }

    private void login(String email, String senha) {
//        chamando o objeto aut para autenticar
        Auth.signInWithEmailAndPassword(email, senha)
//                passando a activity onde estou
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            para onde vamos dirigir ao final da configuração
                            Intent i = new Intent(Login.this, Perfil2.class);
                            startActivity(i);
                        } else {
                            alert("email ou senha errados");
                        }
                    }
                });
    }

    private void alert(String email_ou_senha_errados) {
        Toast.makeText(com.example.project.Login.this, email_ou_senha_errados, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Auth = Conexao.getFirebaseAuth();
    }

    public void setUsuario(EditText usuario) {
        Usuario = usuario;
    }

    public void setSenha(EditText senha) {
        Senha = senha;
    }

    public void setBtnCadastrar(Button btnCadastrar) {
        this.btnCadastrar = btnCadastrar;
    }

    public void setBtnEntrar(Button btnEntrar) {
        this.btnEntrar = btnEntrar;
    }

    public void setTxtRsenha(TextView txtRsenha) {
        this.txtRsenha = txtRsenha;
    }
}
