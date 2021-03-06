package com.example.tccsimsim.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Usuario;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_login, editText_Senha;
    private Button button_login;
    private BDSQLiteHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_login = (EditText)findViewById(R.id.editText_Login);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaLogin);
        button_login = (Button)findViewById(R.id.OkLogin);
        bd = new BDSQLiteHelper(this);

        button_login.setOnClickListener(this);
        criauseradmin();
    }

    private void criauseradmin() {
        if (bd.login("admin") == null) {
            Usuario admin = new Usuario();
            admin.setPermissao("rw");
            admin.setLogin("admin");
            admin.setSenha("admin");
            admin.setNome("Admin");
            bd.addUsuario(admin);
        }
        else {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.OkLogin:
                verificalogin();

                //login();
                break;
        }
    }

    private void verificalogin() {
        String login = editText_login.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();


        if (login.isEmpty() || senha.isEmpty()){

            Toast.makeText(getBaseContext(),"Insira os campos obrigatórios", Toast.LENGTH_LONG).show();


        }else{
                confirmarLogin(login,senha);

            }


        }

    private void confirmarLogin(String login, String senha) {
       // startActivity(new Intent(getBaseContext(),MainActivity.class));

        String parametro = "' "+login+" '%";
        Usuario user = bd.login(login);
        if(user==null){
            Toast.makeText(getBaseContext(),"Usuário não encontrado", Toast.LENGTH_LONG).show();
        }
        else if(user.getSenha().equals(senha)){
            Toast.makeText(getBaseContext(),"Login efetuado com sucesso", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id_usuario",""+user.getId());
            startActivity(intent);
        }
        else{
            Toast.makeText(getBaseContext(),"Senha incorreta", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.commit();
    }
}

