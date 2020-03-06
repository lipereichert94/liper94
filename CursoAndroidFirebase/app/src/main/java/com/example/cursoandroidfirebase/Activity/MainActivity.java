package com.example.cursoandroidfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cursoandroidfirebase.Classes.Usuario;
import com.example.cursoandroidfirebase.DAO.ConfiguracaoFirebase;
import com.example.cursoandroidfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnlogin;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        if (usuarioLogado()) {
        Intent intentMinhaConta = new Intent(MainActivity.this,PrincipalActivity.class);
        abrirNovaActivity(intentMinhaConta);
        } else {
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")) {
                        usuario = new Usuario();
                        usuario.setEmail(edtEmail.getText().toString());
                        usuario.setSenha(edtSenha.getText().toString());
                        validarLogin();
                    } else {
                        Toast.makeText(MainActivity.this, "Preencha os campos de e-mail e senha", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(),usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this,"Login Efetuado com sucesso", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(MainActivity.this,"Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void abrirTelaPrincipal(){

        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }
    public Boolean usuarioLogado(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            return true;
        }else{
            return false;
        }
    }
    public void abrirNovaActivity (Intent intent){
        startActivity(intent);
    }
}
