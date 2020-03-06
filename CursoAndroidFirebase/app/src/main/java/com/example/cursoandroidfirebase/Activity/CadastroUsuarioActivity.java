package com.example.cursoandroidfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.cursoandroidfirebase.Classes.Usuario;
import com.example.cursoandroidfirebase.DAO.ConfiguracaoFirebase;
import com.example.cursoandroidfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha1;
    private EditText senha2;
    private EditText nome;
    private RadioButton rbAdmin;
    private RadioButton rbAtend;
    private Button btncadastrar;
    private Button btncancelar;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        email = (EditText) findViewById(R.id.edtCadEmail);
        senha1 = (EditText) findViewById(R.id.edtCadSenha);
        senha2 = (EditText) findViewById(R.id.edtCadSenha2);
        nome = (EditText) findViewById(R.id.edtCadNome);
        rbAdmin = (RadioButton)findViewById(R.id.rbadmin);
        rbAtend = (RadioButton)findViewById(R.id.rbAtend);
        btncadastrar = (Button)findViewById(R.id.btnCadastrar);
        btncancelar = (Button)findViewById(R.id.btnCancelar);

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(senha1.getText().toString().equals(senha2.getText().toString())){
                    usuario = new Usuario();
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    usuario.setNome(nome.getText().toString());

                    if(rbAdmin.isChecked()){
                        usuario.setTipoUsuario("Administrador");
                    } else if(rbAtend.isChecked()){
                        usuario.setTipoUsuario("Atendente");
                    }
                    cadastrarUsuario();
                }
                else{
                    Toast.makeText(CadastroUsuarioActivity.this,"As senhas não se correspondem!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    insereUsuario(usuario);
                }else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte contendo no mínimo 8 caracteres e que contenha letras e números";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esse e-mail já está cadastrado";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this,"Erro: "+erroExcecao,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean insereUsuario(Usuario usuario){
        try{
            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
            reference.push().setValue(usuario);

            Toast.makeText(CadastroUsuarioActivity.this,"Usuário cadastrado com sucesso",Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e){
            Toast.makeText(CadastroUsuarioActivity.this,"Erro ao gravar o usuário",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }

    }
}
