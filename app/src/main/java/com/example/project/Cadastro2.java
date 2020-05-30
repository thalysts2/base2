package com.example.projeto_semestre2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button btnRegistrar,btnVoltar;
    //objeto firebase
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
//                criando metodo para constriur ususarios
                criarUser(email,senha);
            }
        });
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email,senha)
//qual tela eu me encontro
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        esse objeto vai dizer se deu certo ou nao
                        if(task.isSuccessful()){
                            alert("usuario cadastrado");
                            Intent i = new Intent(Cadastro.this,Pefil.class);
                            finish();
                        }else{
                            alert("Erro de cadastro");
                        }

                    }
                });
    }
    private void alert(String msg){
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail=findViewById(R.id.)
        colocar os outros
    }
    @Override
    protected void onStart(){
        super.onStart();
//        buscando a conexão
        auth= Conexao.getFirebaseAuth();

    }
}