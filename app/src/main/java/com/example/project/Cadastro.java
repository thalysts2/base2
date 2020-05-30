package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

//        instaciando
        Auth = FirebaseAuth.getInstance();

        Nome = findViewById(R.id.editTextNome);
        Cpf = findViewById(R.id.editTextCpf);
        Nascimento= findViewById(R.id.editTextNascimento);
        Email=findViewById(R.id.editTextEmail);
        Csenha=findViewById(R.id.editTextCsenha);
    }
    public void salvar(View view) {
//        quando for salvar, vai
        final String nome = Nome.getText().toString().trim();
        final String email= Email.getText().toString().trim();
        final String cpf = Cpf.getText().toString().trim();
        final String nascimento= Nascimento.getText().toString().trim();
        String senha= Csenha.toString().trim();
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
//        se nem um deles estiverem vazios
        Auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//         ou seja se for um sucesso,vai cadastrar o nosso usuario.
                if(task.isSuccessful()){
//                    pegando o usuario atual
                    FirebaseUser user= Auth.getCurrentUser();
//                    quando criarmos o usario, podemos salvar atraves desse codigo
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    pegando uma referencia no database
                    DatabaseReference userRef = database.getReference("users/" + user.getUid());
                    Map<String,Object> userInfos = new HashMap<>();
                    userInfos.put("usuario", nome);
                    userInfos.put("email",email);
                    userInfos.put("nascimento",nascimento);
                    userInfos.put("cpf", cpf);
//                    pegando a referencia
                    userRef.setValue(userInfos);
                    finish();
//                    caso contrario, se aconteceu algum erro
                }else{
                    try {
                        throw task.getException();
//                        caso seja uma essa fraca
//                        no firebase exige pelo menos 8 digitos na senha
                    } catch (FirebaseAuthWeakPasswordException e ){
                        Csenha.setError("Senha fraca!");
//                        pedindo uma atenção no campo senha
                        Csenha.requestFocus();
//                        caso se o email for invalido, codigo abaixo
                    }catch (FirebaseAuthInvalidCredentialsException e ){
                        Email.setError("Email invalido!");
                        Email.requestFocus();
//                         quando o usuario já possui um email existente registrada
                    } catch (FirebaseAuthUserCollisionException e ){
                        Email.setError("Email já existe");
                        Email.requestFocus();
//                        algum erro que não é nenhum acima
                    }catch (Exception e ){
                        Log.e("Cadastro",e.getMessage());
                    }

                }
            }
        });
    }
    public void voltar(View view){
//        voltando para teal de login com image button!
        Intent i = new Intent(Cadastro.this, Login.class);

//        executando
        startActivity(i);
    }

}
