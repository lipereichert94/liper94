package com.example.firebaseautenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_Email, editText_Senha;
    private Button button_login, button_RecuperarSenha;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        editText_Email = (EditText)findViewById(R.id.editText_EmailLogin);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaLogin);
        button_login = (Button)findViewById(R.id.OkLogin);
        button_RecuperarSenha = (Button)findViewById(R.id.RecuperarSenhaLogin);

        button_login.setOnClickListener(this);
        button_RecuperarSenha.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.OkLogin:

                loginEmail();
                break;

            case R.id.RecuperarSenhaLogin:
                recuperarSenha();

                break;


        }
    }

    private void recuperarSenha(){

        String email = editText_Email.getText().toString().trim();

        if (email.isEmpty()){

            Toast.makeText(getBaseContext(),"Insira pelo menos seu e-mail para poder Recuperar sua senha", Toast.LENGTH_LONG).show();


        }else{


            enviarEmail(email);
        }

    }
    private void  enviarEmail(String email){



        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Enviamos uma MSG para o seu email com um link para você redefinir a sua senha",
                        Toast.LENGTH_LONG).show();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                String erro = e.toString();

                Util.opcoesErro(getBaseContext(),erro);


            }
        });


    }


    private void loginEmail(){

        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();



        if (email.isEmpty() || senha.isEmpty()){

            Toast.makeText(getBaseContext(),"Insira os campos obrigatórios", Toast.LENGTH_LONG).show();


        }else{


            if (Util.statusInternet_MoWi(this)){


                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


                confirmarLoginEmail(email,senha);

            }else{

                Toast.makeText(getBaseContext(),"Erro - Verifique se sua Wiffi ou 3G está funcionando", Toast.LENGTH_LONG).show();

            }


        }

    }

    private void confirmarLoginEmail(String email, String senha){



        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    startActivity(new Intent(getBaseContext(),PrincipalActivity.class));
                    Toast.makeText(getBaseContext(),"Usuario Logado com Sucesso", Toast.LENGTH_LONG).show();
                    finish();
                }else{


                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(),resposta);
                    // Toast.makeText(getBaseContext(),"Erro ao Logar usuario", Toast.LENGTH_LONG).show();

                }

            }
        });



    }
}
