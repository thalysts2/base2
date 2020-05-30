package com.example.project;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Cadastro extends Activity {
    //    objeto resposanvel por autorizar os nossos usaurios
    private FirebaseAuth Auth;

    private EditText Nome;
    private EditText Cpf;
    private EditText Nascimento;
    private EditText Email;
    private EditText Csenha;
    private ImageButton Voltar;
    private Button Salvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Auth = FirebaseAuth.getInstance();

        Nome = findViewById(R.id.editTextNome);
        Cpf = findViewById(R.id.editTextCpf);
        Nascimento= findViewById(R.id.editTextNascimento);
        Email=findViewById(R.id.editEmail);
        Csenha=findViewById(R.id.editTextCsenha);

        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                final String email = Email.getText().toString().trim();
               final String nome = Nome.getText().toString().trim();
                String senha = Csenha.getText().toString().trim();
                final String cpf = Cpf.getText().toString().trim();
                final String nascimento = Nascimento.getText().toString().trim();

//                criando metodo para constriur ususarios
                criarUser(email, nome, senha,cpf,nascimento);
                if(nome.equals("")){
                    Nome.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
                    return;
                }
//        abaxo irei tratar dos casos que os campos não foram preenchidos, dando um aviso para prencheer
                if(email.equals("")) {
                    Email.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
                    return;
                }
                if(cpf.equals("")) {
                    Cpf.setError("Preencha este campo!");
//            vai pedir um um foco neste campo

                    return;
                }
                if(nascimento.equals("")) {
                    Nascimento.setError("Preencha este campo!");
//            vai pedir um um foco neste campo

                    return;
                }
                if(senha.equals("")) {
                    Csenha.setError("Preencha este campo!");
                    Csenha.requestFocus();
//            vai pedir um um foco neste campo

                    return;
                }

            }
        });
    }

    private void criarUser(final String email, final String nome, String senha,final String nascimento, final String cpf ) {
        Auth.createUserWithEmailAndPassword(email,senha)
//qual tela eu me encontro
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        esse objeto vai dizer se deu certo ou nao
                        if (task.isSuccessful()) {
//                            pegando o usuario atual
                            FirebaseUser user= Auth.getCurrentUser();
//                    quando criarmos o usario, podemos salvar atraves desse codigo
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    pegando uma referencia no database
                            DatabaseReference userRef = database.getReference("users/" + user.getUid());
                            Map<String,Object> userInfos = new HashMap<>();
                            userInfos.put("usuario", nome);
                            userInfos.put("email",email);
                            userInfos.put("nascimento",nascimento);
                            userInfos.put("cpf",cpf);
                            
//                    pegando a referencia
                            userRef.setValue(userInfos);
                            finish();

                            alert("usuario cadastrado");
                            Intent i = new Intent(Cadastro.this, Perfil2.class);
                            finish();
                        } else {
                            alert("Erro de cadastro");
                        }

                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(Cadastro.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        Email = findViewById(R.id.editEmail);
        Nome= findViewById(R.id.editTextNome);
        Csenha= findViewById(R.id.editTextCsenha);
        Nascimento= findViewById(R.id.editTextNascimento);
        Cpf = findViewById(R.id.editTextCpf);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        buscando a conexão
        Auth = Conexao.getFirebaseAuth();

    }
}
