package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Usuario;

public class Cadastro_Usuario extends Fragment implements View.OnClickListener
{
    View minhaView;
    private Button btnsalvar;
    private EditText nome,login,senha;
    private BDSQLiteHelper bd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        minhaView = inflater.inflate(R.layout.layout_cadastro_usuario, container, false);
        bd = new BDSQLiteHelper(getActivity());
        btnsalvar = (Button)minhaView.findViewById(R.id.button_CadastrarUsuario);
        nome = (EditText)minhaView.findViewById(R.id.editText_NomeCadastro);
        login = (EditText)minhaView.findViewById(R.id.editText_LoginCadastro);
        senha = (EditText)minhaView.findViewById(R.id.editText_SenhaCadastro);
        btnsalvar.setOnClickListener(this);



        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.button_CadastrarUsuario:
                Log.d("----->", "CLICOU BOTAO CADASTRAR USUARIO");
                Toast.makeText(getActivity(), "CLICOU BOTAO CADASTRAR USUARIO",
                        Toast.LENGTH_LONG).show();
                SalvarUsuario();
                break;

        }
}

    private void SalvarUsuario() {
        Usuario user = new Usuario();
        user.setNome(nome.getText().toString());
        user.setLogin(login.getText().toString());
        user.setSenha(senha.getText().toString());
        bd.addUsuario(user);
        Toast.makeText(getActivity(), "Usuário criado com sucesso!",
                Toast.LENGTH_LONG).show();
    }
    }
