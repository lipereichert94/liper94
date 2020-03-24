package com.example.tccsimsim.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tccsimsim.R;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_login, editText_Senha;
    private Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_login = (EditText)findViewById(R.id.editText_Login);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaLogin);
        button_login = (Button)findViewById(R.id.OkLogin);

        button_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.OkLogin:
                startActivity(new Intent(getBaseContext(),MainActivity.class));

                //login();
                break;
        }
    }
}
