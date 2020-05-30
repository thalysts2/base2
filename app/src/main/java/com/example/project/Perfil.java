package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {
    private TextView textEmail, textID;
    private Button btnLogOut;

    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_perfil);
        inicializaComponentes();
//        ,etodo para onclick do botaoLogout
        eventoClick();

    }

    private void eventoClick() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logOut();
                finish();
            }
        });
    }

    private void inicializaComponentes() {
        textEmail = findViewById();
        textID= findViewById();
        btnLogOut = findViewById();
    }

    @Override
    protected void onStart(){
        super.onStart();
//        salvando as informações
        auth = Conexao.getFirebaseAuth();
//        informações do usuario
        user= Conexao.getFirebaseUser();
        verificaUser();


    }

    private void verificaUser() {
//        caso seja igual a null eu preciso finalizar
        if (user == null){
            finish();
//            caso não eu vou pegar as informações contidas desse usuario e exibilas no edit email e no textid
        }else{
            textEmail.setText("Email"+user.getEmail());
            textID.setText("ID:"+user.getUid());
        }
    }
}
