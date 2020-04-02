package com.example.tccsimsim.project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Usuario;

public class Cadastro_Estabelecimento extends Fragment implements View.OnClickListener {
    View minhaView;
    private Button btnsalvar, btnremover;
    private EditText nome;
    private BDSQLiteHelper bd;
    private int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_cadastro_estabelecimento, container, false);
        bd = new BDSQLiteHelper(getActivity());
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarEstabelecimento);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerEstabelecimento);
        nome = (EditText) minhaView.findViewById(R.id.editText_NomeCadastroEstabelecimento);
        btnsalvar.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        readBundle(getArguments());
        if (id != 0) {
            btnremover.setText("Remover");
            Estabelecimento estabelecimento = bd.getEstabelecimento(id);
            nome.setText(estabelecimento.getNome());
        }
        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarEstabelecimento:
                SalvarEstabelecimento();
                break;
            case R.id.button_removerEstabelecimento:
                if (id != 0) {
                    RemoverEstabelecimento();
                } else {
                    limparcampos();
                }
                break;
        }
    }

    private void RemoverEstabelecimento() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Estabelecimento finalestabelecimento = bd.getEstabelecimento(id);
                        bd.deleteEstabelecimento(finalestabelecimento);
                        Toast.makeText(getActivity(), "Estabelecimento Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Estabelecimento());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void limparcampos() {
        nome.setText("");
    }

    private void SalvarEstabelecimento() {
        if (id != 0) {
            //alterar
            if(verificacampos()){
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setId(id);
                estabelecimento.setNome(nome.getText().toString());
                bd.updateEstabelecimento(estabelecimento);
                Toast.makeText(getActivity(), "Estabelecimento alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Estabelecimento());
                ft.commit();
}
        }
        //gravar novo estabelecimento
        else {
            if(verificacampos()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setNome(nome.getText().toString());
                bd.addEstabelecimento(estabelecimento);
                Toast.makeText(getActivity(), "Estabelecimento criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Estabelecimento());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
        if(nome.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Favor preencher o nome do estabelecimento!",
                    Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    public static Cadastro_Estabelecimento newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        Cadastro_Estabelecimento fragment = new Cadastro_Estabelecimento();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }


}

