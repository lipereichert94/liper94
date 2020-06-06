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

import java.text.ParseException;
import java.util.Calendar;

public class Cadastro_Estabelecimento extends Fragment implements View.OnClickListener {
    View minhaView;
    private Button dt_registro,btnsalvar, btnremover,cancelar;
    private EditText nome,nome_fantasia,classificacao,cnpj,inscricao_estadual,inscricao_municipal,endereco,endereco_eletronico,telefone;
    private BDSQLiteHelper bd;
    private int id = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_cadastro_estabelecimento, container, false);
        bd = new BDSQLiteHelper(getActivity());
        dt_registro = (Button) minhaView.findViewById(R.id.btn_dtregistro_Cadastro_Estabelecimento);
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarEstabelecimento);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerEstabelecimento);
        cancelar = (Button) minhaView.findViewById(R.id.button_CancelarEstabelecimento);
        nome = (EditText) minhaView.findViewById(R.id.editText_NomeCadastroEstabelecimento);
        nome_fantasia = (EditText) minhaView.findViewById(R.id.editText_Nome_Fantasia_CadastroEstabelecimento);
        classificacao = (EditText) minhaView.findViewById(R.id.editText_Classificacao_CadastroEstabelecimento);
        cnpj = (EditText) minhaView.findViewById(R.id.editText_CNPJCadastroEstabelecimento);
        inscricao_estadual = (EditText) minhaView.findViewById(R.id.editText_Inscricao_EstadualCadastroEstabelecimento);
        inscricao_municipal = (EditText) minhaView.findViewById(R.id.editText_Inscricao_MunicipalCadastroEstabelecimento);
        endereco = (EditText) minhaView.findViewById(R.id.editText_Endereco_CadastroEstabelecimento);
        endereco_eletronico = (EditText) minhaView.findViewById(R.id.editText_Endereco_Eletronico_CadastroEstabelecimento);
        telefone = (EditText) minhaView.findViewById(R.id.editText_Telefone_CadastroEstabelecimento);
        btnsalvar.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        setDataAtual();
        readBundle(getArguments());
        if (id != 0) {
            btnremover.setText("Remover");
            Estabelecimento estabelecimento = null;
            try {
                estabelecimento = bd.getEstabelecimento(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nome.setText(estabelecimento.getNome());
            nome_fantasia.setText(""+estabelecimento.getNome_fantasia());
            classificacao.setText(""+estabelecimento.getClassificacao());
            cnpj.setText(""+estabelecimento.getCnpj());
            inscricao_estadual.setText(""+estabelecimento.getInscricao_estadual());
            inscricao_municipal.setText(""+estabelecimento.getInscricao_municipal());
            endereco.setText(""+estabelecimento.getEndereco());
            endereco_eletronico.setText(""+estabelecimento.getFone());
            dt_registro.setText(""+estabelecimento.getDt_registro());
            telefone.setText(""+estabelecimento.getFone());
        }
        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarEstabelecimento:
                try {
                    SalvarEstabelecimento();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_CancelarEstabelecimento:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Estabelecimento());
                ft.commit();
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
                         Estabelecimento finalestabelecimento = null;
                        try {
                            finalestabelecimento = bd.getEstabelecimento(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int exlusao = 0;
                        exlusao = bd.deleteEstabelecimento(finalestabelecimento);
                        if(exlusao==1) {
                            Toast.makeText(getActivity(), "Estabelecimento Excluído com sucesso!",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Erro ao excluir estabelecimento pois está sendo utilizado" +
                                            "em outros registros!",
                                    Toast.LENGTH_LONG).show();
                        }
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

    private void SalvarEstabelecimento() throws ParseException {
        if (id != 0) {
            //alterar
            if(verificacampos()){
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setId(id);
                estabelecimento.setNome(nome.getText().toString());
                estabelecimento.setNome_fantasia(nome_fantasia.getText().toString());
                estabelecimento.setClassificacao(classificacao.getText().toString());
                estabelecimento.setCnpj(Integer.parseInt(cnpj.getText().toString()));
                estabelecimento.setInscricao_estadual(Integer.parseInt(inscricao_estadual.getText().toString()));
                estabelecimento.setInscricao_municipal(Integer.parseInt(inscricao_municipal.getText().toString()));
                estabelecimento.setEndereco(endereco.getText().toString());
                estabelecimento.setEndereco_eletronico(endereco_eletronico.getText().toString());
                estabelecimento.setDt_registro(dt_registro.getText().toString());
                estabelecimento.setFone(telefone.getText().toString());
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
                estabelecimento.setNome_fantasia(nome_fantasia.getText().toString());
                estabelecimento.setClassificacao(classificacao.getText().toString());
                estabelecimento.setCnpj(Integer.parseInt(cnpj.getText().toString()));
                estabelecimento.setInscricao_estadual(Integer.parseInt(inscricao_estadual.getText().toString()));
                estabelecimento.setInscricao_municipal(Integer.parseInt(inscricao_municipal.getText().toString()));
                estabelecimento.setEndereco(endereco.getText().toString());
                estabelecimento.setEndereco_eletronico(endereco_eletronico.getText().toString());
                estabelecimento.setDt_registro(dt_registro.getText().toString());
                estabelecimento.setFone(telefone.getText().toString());
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
    private void setDataAtual() {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        if(day == 1 || day == 2 || day == 3 || day ==4 || day ==5 || day ==6 || day ==7 || day ==8 || day ==9 ){
            dt_registro.setText("0"+day+"-");
        }
        else{
            dt_registro.setText(day+"-");
        }
        if(month == 0 || month == 1 || month == 2 || month == 3 || month ==4 || month ==5 || month ==6 || month ==7 || month ==8 || month ==9 ) {
            dt_registro.setText(dt_registro.getText()+"0"+(month+1)+"-"+year);
        }
        else{
            dt_registro.setText(""+dt_registro.getText()+(month+1)+"-"+year);
        }
        //  dt_registro.setText(day + "-" + (month + 1) + "-" + year);

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

