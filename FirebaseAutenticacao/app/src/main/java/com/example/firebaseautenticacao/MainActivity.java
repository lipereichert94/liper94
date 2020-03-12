package com.example.firebaseautenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_login, button_Cadastrar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = (Button) findViewById(R.id.button_login);
        button_Cadastrar = (Button) findViewById(R.id.button_cadastrar);

        button_login.setOnClickListener(this);
        button_Cadastrar.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        estadoautenticacao();
    }

    private void estadoautenticacao() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(getBaseContext(),"Usuário"+user.getEmail()+" está logado",Toast.LENGTH_LONG).show();

                }else{
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                user = auth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(this,LoginEmailActivity.class));

                }
                else{
                    startActivity(new Intent(this,PrincipalActivity.class));

                }

                break;

            case R.id.button_cadastrar:
                Intent intent = new Intent(this,CadastrarActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
