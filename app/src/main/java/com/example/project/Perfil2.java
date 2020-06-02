package com.example.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil2 extends Activity {
    private TextView textViewEmail,textViewUid,textViewCpf,textViewNascimento;
    private Button Logout;

    private FirebaseAuth Auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_perfil);
        inicializaComponentes();
//        metodo para onclick do botaoLogout
        eventoClick();

    }

    private void eventoClick() {
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logOut();
                finish();
            }
        });
    }

    private void inicializaComponentes() {
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewUid= findViewById(R.id.textViewUid);
        textViewCpf= findViewById(R.id.textViewCpf);
        textViewNascimento= findViewById(R.id.textViewNascimento);
        Logout = findViewById(R.id.buttonLogout);
    }

    @Override
    protected void onStart(){
        super.onStart();
//        salvando as informações
        Auth = Conexao.getFirebaseAuth();
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
            textViewEmail.setText("Email"+user.getEmail());
            textViewUid.setText("ID:"+user.getUid());
            textViewCpf.setText("ID:"+user.getUid());
            textViewNascimento.setText("ID:"+user.getUid());

        }
    }
}