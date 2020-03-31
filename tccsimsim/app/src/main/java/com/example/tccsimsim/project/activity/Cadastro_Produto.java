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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;

public class Cadastro_Produto extends Fragment implements View.OnClickListener {
    View minhaView;
    private Button btnescolherestabelecimento,btnsalvar, btnremover;
    private EditText nome;
    private BDSQLiteHelper bd;
    private int id = 0;
    private int id_estabelecimento = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_cadastro_produto, container, false);
        bd = new BDSQLiteHelper(getActivity());
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarProduto);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerProduto);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_produto);
        nome = (EditText) minhaView.findViewById(R.id.editText_NomeCadastroProduto);
        btnsalvar.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        btnescolherestabelecimento.setOnClickListener(this);
        readBundle(getArguments());
        if (id_estabelecimento != -1 ) {
            Estabelecimento estabelecimento = bd.getEstabelecimento(id_estabelecimento);
            btnescolherestabelecimento.setText(estabelecimento.getNome());
        }
        if (id != 0) {
            btnremover.setText("Remover");
            Produto produto = bd.getProduto(id);
            nome.setText(produto.getNome());
           // Estabelecimento estabelecimento = bd.getEstabelecimento(id_estabelecimento);
           // btnescolherestabelecimento.setText(estabelecimento.getNome());

        }
        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarProduto:
                SalvarProduto();
                break;
            case R.id.button_EscolherEstabelecimento_cadastro_produto:
                EscolherEstabelecimento();
                break;
            case R.id.button_removerProduto:
                if (id != 0) {
                    RemoverProduto();
                } else {
                    limparcampos();
                }
                break;
        }
    }

    private void EscolherEstabelecimento() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.d("----->", "No frame escolher estabelecimento chegou passando id do produto ="+id);

        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Estabelecimento().newInstance(id,"cadastro_produto",0));
        ft.commit();

    }

    private void RemoverProduto() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Produto finalproduto = bd.getProduto(id);
                        bd.deleteProduto(finalproduto);
                        Toast.makeText(getActivity(), "Produto Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Produto());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void limparcampos() {
        nome.setText("");
    }

    private void SalvarProduto() {
        if (id != 0) {
            //alterar
            Log.d("----->", "No frame alterar produtochegou");
                Estabelecimento estabelecimento = new Estabelecimento();
                Produto produto = new Produto();
                produto.setId(id);
                produto.setNome(nome.getText().toString());
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                produto.setEstabelecimento(estabelecimento);
            Log.d("----->", "No frame alterar produtochegou e id_estabelecimento é "+id_estabelecimento);

                bd.updateProduto(produto);
                Toast.makeText(getActivity(), "Produto alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Produto());
                ft.commit();

        }
        //gravar novo usuario
        else {
            Estabelecimento estabelecimento = new Estabelecimento();
            Produto produto = new Produto();
            produto.setNome(nome.getText().toString());
            estabelecimento = bd.getEstabelecimento(id_estabelecimento);
            produto.setEstabelecimento(estabelecimento);
            Log.d("----->", "No frame cadastrar produtochegou e id_estabelecimento é "+id_estabelecimento);


            bd.addProduto(produto);
                Toast.makeText(getActivity(), "Produto criado com sucesso!",
                        Toast.LENGTH_LONG).show();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.conteudo_fragmento, new Lista_Produto());
            ft.commit();
        }
    }

    public static Cadastro_Produto newInstance(int id,int id_estabelecimento) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_estabelecimento", id_estabelecimento);

        Cadastro_Produto fragment = new Cadastro_Produto();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
            id_estabelecimento = bundle.getInt("id_estabelecimento");

        }
    }


}

