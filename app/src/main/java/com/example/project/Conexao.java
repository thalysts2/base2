package com.example.project;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


//classe que conecta com banco e passa os dados para o app
public class Conexao {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    //    deixando o construtor em private para que ngm possa acessar
    private Conexao() {

    }

    //    metodo publico que retorna o objeto tipo firebase
    public static FirebaseAuth getFirebaseAuth() {
// se o objeto for nulo eu devo criar
        if (firebaseAuth != null) {
            inicializarFirebaseAuth();

        }
        return firebaseAuth;
    }

    private static void inicializarFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    firebaseUser = user;
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public static FirebaseUser  getFirebaseUser(){
        return firebaseUser;
    }
    //    responsavel por fazer a saida do usuario
    public static void logOut(){
        firebaseAuth.signOut();

    }
}
