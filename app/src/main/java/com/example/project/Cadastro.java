package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import static com.example.project.R.layout.activity_cadastro;

/**
 *
 */
public class Cadastro extends Activity {
    //    objeto resposanvel por autorizar os nossos usaurios
    private FirebaseAuth mAuth;

    private EditText Nome;
    private EditText Cpf;
    private EditText Nascimento;
    private EditText Email;
    private EditText Csenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_cadastro);
//        instaciando
        mAuth = FirebaseAuth.getInstance();

        Nome = findViewById(R.id.editTextNome);
        Cpf = findViewById(R.id.editTextCpf);
        Nascimento= findViewById(R.id.editTextNascimento);
        Email=findViewById(R.id.editTextEmail);
        Csenha=findViewById(R.id.editTextCsenha);
    }
    //
    public void salvar(View view) {
//        quando for salvar, vai
        String nome = Nome.getText().toString().trim();
        String email= Email.getText().toString().trim();
        String cpf = Cpf.getText().toString().trim();
        String nascimento= Nascimento.toString().trim();
        String senha= Csenha.toString().trim();
        if(nome.equals("")){
            Nome.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
            Nome.setError("Preencha este campo");
            return;
        }
//        abaxo irei tratar dos casos que os campos não foram preenchidos, dando um aviso para prencheer
        if(email.equals("")) {
            Email.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
            Email.setError("Preencha este campo");
            return;
        }
        if(cpf.equals("")) {
            Cpf.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
            Cpf.setError("Preencha este campo");
            return;
        }
        if(nascimento.equals("")) {
            Nascimento.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
            Nascimento.setError("Preencha este campo");
            return;
        }
        if(senha.equals("")) {
            Csenha.setError("Preencha este campo!");
//            vai pedir um um foco neste campo
            Csenha.setError("Preencha este campo");
            return;
        }
//        se nem um deles estiverem vazios
        mAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//         ou seja se for um sucesso,vai cadastrar o nosso usuario.
                if(task.isSuccessful()){
//                    caso contrario, se aconteceu algum erro
                }else{
                    try {
                        throw task.getException();
//                        caso seja uma essa fraca
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
                        Log.e("Cadastro",e.getLocalizedMessage());
                    }
                }
            }
        });
    }
}
