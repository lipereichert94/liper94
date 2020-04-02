package com.example.tccsimsim.project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Usuario;

public class Cadastro_Usuario extends Fragment implements View.OnClickListener {
    View minhaView;
    private Button btnsalvar, btnremover;
    private EditText nome, login, senha, confirmar_senha;
    private BDSQLiteHelper bd;
    private int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_cadastro_usuario, container, false);
        bd = new BDSQLiteHelper(getActivity());
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarUsuario);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerUsuario);
        nome = (EditText) minhaView.findViewById(R.id.editText_NomeCadastro);
        login = (EditText) minhaView.findViewById(R.id.editText_LoginCadastro);
        senha = (EditText) minhaView.findViewById(R.id.editText_SenhaCadastro);
        confirmar_senha = (EditText) minhaView.findViewById(R.id.editText_SenhaRepetirCadastro);
        btnsalvar.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        readBundle(getArguments());
        if (id != 0) {
            btnremover.setText("Remover");
            Usuario user = bd.getUsuario(id);
            nome.setText(user.getNome());
            login.setText(user.getLogin());
            senha.setText(user.getSenha());
            confirmar_senha.setText(user.getSenha());
        }
        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarUsuario:
                SalvarUsuario();
                break;
            case R.id.button_removerUsuario:
                if (id != 0) {
                    removerusuario();
                } else {
                    limparcampos();
                }
                break;
        }
    }

    private void removerusuario() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Usuario finaluser = bd.getUsuario(id);
                        bd.deleteUsuario(finaluser);
                        Toast.makeText(getActivity(), "Usuário Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Usuario());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void limparcampos() {
        nome.setText("");
        login.setText("");
        senha.setText("");
        confirmar_senha.setText("");
    }

    private void SalvarUsuario() {
        if (id != 0) {
            //alterar

            if (!senha.getText().toString().equals(confirmar_senha.getText().toString())) {
                Toast.makeText(getActivity(), "As senhas não correspondem",
                        Toast.LENGTH_LONG).show();
            } else {

                Usuario user = new Usuario();
                user.setId(id);
                user.setNome(nome.getText().toString());
                user.setLogin(login.getText().toString());
                user.setSenha(senha.getText().toString());
                bd.updateUsuario(user);
                limparcampos();
                Toast.makeText(getActivity(), "Usuário alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Usuario());
                ft.commit();
            }
        }
        //gravar novo usuario
        else {

            if (bd.login(login.getText().toString()) != null) {
                Toast.makeText(getActivity(), "Nome de login já utilizado, favor inserir outro",
                        Toast.LENGTH_LONG).show();
            } else if (!senha.getText().toString().equals(confirmar_senha.getText().toString())) {
                Toast.makeText(getActivity(), "As senhas não correspondem",
                        Toast.LENGTH_LONG).show();
            } else {
                Usuario user = new Usuario();
                user.setNome(nome.getText().toString());
                user.setLogin(login.getText().toString());
                user.setSenha(senha.getText().toString());
                bd.addUsuario(user);
                limparcampos();
                Toast.makeText(getActivity(), "Usuário criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Usuario());
                ft.commit();

            }
        }
    }

    public static Cadastro_Usuario newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        Cadastro_Usuario fragment = new Cadastro_Usuario();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }


}

