package com.example.firebaseautenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_Email,editText_Senha,editText_SenhaRepetir;
    private Button button_cadastrar,button_cancelar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_Email = (EditText)findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaCadastro);
        editText_SenhaRepetir = (EditText)findViewById(R.id.editText_SenhaRepetirCadastro);
        button_cadastrar = (Button)findViewById(R.id.button_CadastrarUsuario);
        button_cancelar = (Button)findViewById(R.id.button_Cancelar);
        button_cadastrar.setOnClickListener(this);

        auth = getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_CadastrarUsuario:
                cadastrar();
                break;
        }
    }

    private void cadastrar() {

        String email = editText_Email.getText().toString().trim(); //trim remove os espaços extras que usuário preencheu
        String senha = editText_Senha.getText().toString().trim();
        String confirmaSenha = editText_SenhaRepetir.getText().toString().trim();


        if(email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()){
            Toast.makeText(getBaseContext(),"Erro! Preencha todos os campos!",Toast.LENGTH_LONG).show();
        }else{

            if(senha.equals(confirmaSenha)){
                if(Util.statusInternet_MoWi(this)) {

                    criarUsuario(email, senha);
                }else {
                    Toast.makeText(getBaseContext(),"Erro - Verifique se sua Wiffi ou 3G está funcionando",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getBaseContext(),"Erro! Senhas diferentes!",Toast.LENGTH_LONG).show();
            }

        }





    }

    private void criarUsuario(String email,String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful() == true) {
                    startActivity(new Intent(getBaseContext(),PrincipalActivity.class));
                    Toast.makeText(getBaseContext(), "Cadastrou com sucesso", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(),resposta);
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
