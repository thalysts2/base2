package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.project.R.layout.activity_main;

public class MainActivity extends Activity {
//    Firebase é uma plataforma Web que nos permite salvar os dados de nossas aplicações na base de dados NoSQL provida por eles.
    //    criando o firebase
    private FirebaseAuth Auth;
    private EditText Usuario;
    private EditText Senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
//        instaciando o firebase
        Auth = FirebaseAuth.getInstance();

        Usuario = findViewById(R.id.editTextUsuario);
        Senha = findViewById(R.id.editTextCsenha);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //pegando o usuario atual QUE É mAuth
        FirebaseUser currentUser = Auth.getCurrentUser();
//        com esse usuario atual nós vamos falar para nossa view para se comportar de acordo com esse usuario
        updateUI(currentUser);
    }

//    criando a update
    private void updateUI(FirebaseUser user){
        if(user!= null){
//            passar para a proxima tela! ses nós temos o usuario logado nós vamos passar para a proxima tela
        }
    }
//    foi colocado na activity(view) no buttonEntrar o metodo Onclick e está adiconando esse comando aqui para reconhecer
    public void login(View view){
        String email = Usuario.getText().toString().trim();
        String senha= Senha.getText().toString().trim();
//        se o campo do email esta vazio vamos falar o seguinte mensagem
//        peguei a string email
        if(email.equals("")){
            Usuario.setError("Preencha este campo!");
            return;
//            coloquei o return caso um deles estiverem vazios para que o usuario não tente preencher o campo, para apenas que de o erro
        }
//        se o campo senha estiver vazio irá passar a seguinte mensagem de erro
//        peguei a String senha
        if(senha.equals("")){
            Senha.setError("Preencha este campo!");
            return;
        }
//        agora se os dois estiverem preenchidos o usuario pode fazer o login
        Auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                entrara quando o login for completo if(task.isSuccessful()){}
                if(task.isSuccessful()){
//                    então se deu tudo certo, posso pegar e dar um updateUI no usuario
//                    ou seja nós vamos passar para a proxima tela
                    updateUI(Auth.getCurrentUser());
//                    caso der errado
                } else {
//                    então se der errado aparecerá o usuario ou senha incorreta
                    Toast.makeText(MainActivity.this,"Usuário ou senha incorreta!",Toast.LENGTH_SHORT).show();
//                    direcionando para o update e deixnado como null(nulo) ou seja não tem usuario
                    updateUI(null);
                }
            }
        });
    }

//colocando o  cadastro
    public void cadastro(View view){
//        ir para teal de cadastro!
        Intent i = new Intent(MainActivity.this, Cadastro.class);
//        executando
        startActivity(i);
    }
}
