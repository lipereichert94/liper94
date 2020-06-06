package com.example.tccsimsim.project.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Analise_Laboratorial;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;

import java.text.ParseException;
import java.util.Calendar;

public class Cadastro_Analise_Laboratorial extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherproduto, btnsalvar, btnremover,dt_coleta,dt_nova_coleta,btncancelar;
    private BDSQLiteHelper bd;
    private EditText notificacao;
    private RadioButton conforme,naoconforme,conforme_nova_coleta,naoconforme_nova_coleta,fisicoquimico,microbiologico;
    private int id = 0;
    private int id_produto = -1;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_analise_laboratorial, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btncancelar = (Button) minhaView.findViewById(R.id.button_CancelarAnaliseLaboratorial);
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarAnaliseLaboratorial);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerANaliseLaboratorial);
        dt_coleta = (Button) minhaView.findViewById(R.id.button_data_coleta_cadastro_AnaliseLaboratorial);
        notificacao = (EditText)minhaView.findViewById(R.id.notificacao_cadastro_analise_laboratorial);
        dt_nova_coleta = (Button) minhaView.findViewById(R.id.button_data_nova_coleta_cadastro_AnaliseLaboratorial);
        conforme = (RadioButton) minhaView.findViewById(R.id.radioconforme_analise_laboratorial_cadastro);
        naoconforme = (RadioButton) minhaView.findViewById(R.id.radionaoconforme_analise_laboratorial_cadastro);
        conforme_nova_coleta = (RadioButton) minhaView.findViewById(R.id.radioconforme_nova_coleta_analise_laboratorial_cadastro);
        naoconforme_nova_coleta = (RadioButton) minhaView.findViewById(R.id.radionaoconforme_nova_coleta_analise_laboratorial_cadastro);
        fisicoquimico = (RadioButton)minhaView.findViewById(R.id.radiotipofisicoquimico_analise_laboratorial_cadastro);
        microbiologico = (RadioButton)minhaView.findViewById(R.id.radiotipomicrobiologico_analise_laboratorial_cadastro);
        btnescolherproduto = (Button) minhaView.findViewById(R.id.button_EscolherProduto_cadastro_AnaliseLaboratorial);
        btnsalvar.setOnClickListener(this);
        dt_coleta.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        dt_nova_coleta.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btnescolherproduto.setOnClickListener(this);
        setDataAtual();
        readBundle(getArguments());

        //verifica se é cadastro ou alteração
        if (id_produto != -1) {
            Produto produto = null;
            try {
                produto = bd.getProduto(id_produto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            btnescolherproduto.setText(produto.getNome());

        }
        if (id != 0) {
            btnremover.setText("Remover");
            Analise_Laboratorial analise_laboratorial = null;

            try {
                analise_laboratorial = bd.getAnaliseLaboratorial(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dt_coleta.setText(analise_laboratorial.getDt_coleta());
            if (analise_laboratorial.getTipo().equals("microbiológico"))
                microbiologico.setChecked(true);
            else
                fisicoquimico.setChecked(true);
            if (analise_laboratorial.getSituacao_coleta().equals("Conforme"))
                conforme.setChecked(true);
            else
                naoconforme.setChecked(true);
            notificacao.setText(analise_laboratorial.getNotificacao());
            if (analise_laboratorial.getSituacao_nova_coleta().equals("Conforme"))
                conforme_nova_coleta.setChecked(true);
            else
                naoconforme_nova_coleta.setChecked(true);
            if(analise_laboratorial.getDt_nova_coleta()!=null){
               dt_nova_coleta.setText(analise_laboratorial.getDt_nova_coleta());
            }

        }

        return minhaView;
    }

    private void setDataAtual() {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        if(day == 1 || day == 2 || day == 3 || day ==4 || day ==5 || day ==6 || day ==7 || day ==8 || day ==9 ){
            dt_coleta.setText("0"+day+"-");
        }
        else{
            dt_coleta.setText(day+"-");
        }
        if(month == 0 || month == 1 || month == 2 || month == 3 || month ==4 || month ==5 || month ==6 || month ==7 || month ==8 || month ==9 ) {
            dt_coleta.setText(dt_coleta.getText()+"0"+(month+1)+"-"+year);
        }
        else{
            dt_coleta.setText(""+dt_coleta.getText()+(month+1)+"-"+year);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarAnaliseLaboratorial:
                try {
                    SalvarAnaliseLaboratorial();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_EscolherProduto_cadastro_AnaliseLaboratorial:
                EscolherProduto();
                break;
            case R.id.button_removerANaliseLaboratorial:
                if (id != 0) {
                    RemoverAnaliseLaboratorial();
                }
                break;
            case R.id.button_data_nova_coleta_cadastro_AnaliseLaboratorial:
                EscolherData();
                break;
            case R.id.button_CancelarAnaliseLaboratorial:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Analise_Laboratorial());
                ft.commit();
                break;
        }
    }
    private void EscolherData() {
        Log.d("----->", "Entrou no método escolherdata");

        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);


        dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int myear, int mMonth, int mDay) {
                if(mDay == 1 || mDay == 2 || mDay == 3 || mDay ==4 || mDay ==5 || mDay ==6 || mDay ==7 || mDay ==8 || mDay ==9 ){
                    dt_nova_coleta.setText("0"+mDay+"-");
                }
                else{
                    dt_nova_coleta.setText(mDay+"-");
                }
                if(mMonth == 0 || mMonth == 1 || mMonth == 2 || mMonth == 3 || mMonth ==4 || mMonth ==5 || mMonth ==6 || mMonth ==7 || mMonth ==8 || mMonth ==9 ) {
                    dt_nova_coleta.setText(dt_nova_coleta.getText()+"0"+(mMonth+1)+"-"+myear);
                }
                else{
                    dt_nova_coleta.setText(""+dt_nova_coleta.getText()+(mMonth+1)+"-"+myear);
                }
            }
        }, year,month,day);
        dpd.show();
    }
    private void EscolherProduto() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Produto().newInstance(id,"analise_laboratorial",id));
        ft.commit();

    }

    private void RemoverAnaliseLaboratorial() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Analise_Laboratorial finalanaliselaboratorial = null;
                        try {
                            finalanaliselaboratorial = bd.getAnaliseLaboratorial(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bd.deleteAnaliseLaboratorial(finalanaliselaboratorial);
                        Toast.makeText(getActivity(), "Análise Laboratorial Excluída com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Analise_Laboratorial());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarAnaliseLaboratorial() throws ParseException {
        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Produto produto = new Produto();
                Analise_Laboratorial analise_laboratorial = new Analise_Laboratorial();
                analise_laboratorial.setId(id);
                produto = bd.getProduto(id_produto);
                analise_laboratorial.setDt_coleta(dt_coleta.getText().toString());
                if(dt_nova_coleta.getText().toString().equals("Clique para escolher a data da nova coleta")){
                    analise_laboratorial.setDt_nova_coleta(null);
                }else {
                    analise_laboratorial.setDt_nova_coleta(dt_nova_coleta.getText().toString());
                }
                if(microbiologico.isChecked()==true){
                    analise_laboratorial.setTipo("microbiológico");
                }
                if(fisicoquimico.isChecked()==true){
                    analise_laboratorial.setTipo("físico-químico");
                }
                if(conforme.isChecked()==true){
                    analise_laboratorial.setSituacao_coleta("Conforme");
                }
                if(naoconforme.isChecked()==true){
                    analise_laboratorial.setSituacao_coleta("Não Conforme");
                }
                if(conforme_nova_coleta.isChecked()==true){
                    analise_laboratorial.setSituacao_nova_coleta("Conforme");
                }
                if(naoconforme_nova_coleta.isChecked()==true){
                    analise_laboratorial.setSituacao_nova_coleta("Não Conforme");
                }
                analise_laboratorial.setNotificacao(notificacao.getText().toString());
                analise_laboratorial.setProduto(produto);
                bd.updateAnaliseLaboratorial(analise_laboratorial);
                Toast.makeText(getActivity(), "Análise Laboratorial alterada com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Analise_Laboratorial());
                ft.commit();
            }
        }
        //gravar nova Análise Laboratorial
        else {
            if(verificacampos()) {
                Produto produto = new Produto();
                Analise_Laboratorial analise_laboratorial = new Analise_Laboratorial();
                analise_laboratorial.setId(id);
                produto = bd.getProduto(id_produto);
                analise_laboratorial.setDt_coleta(dt_coleta.getText().toString());
                if(dt_nova_coleta.getText().toString().equals("Clique para escolher a data da nova coleta")){
                    analise_laboratorial.setDt_nova_coleta(null);
                }else {
                    analise_laboratorial.setDt_nova_coleta(dt_nova_coleta.getText().toString());
                }
                if(microbiologico.isChecked()==true){
                    analise_laboratorial.setTipo("microbiológico");
                }
                if(fisicoquimico.isChecked()==true){
                    analise_laboratorial.setTipo("físico-químico");
                }
                if(conforme.isChecked()==true){
                    analise_laboratorial.setSituacao_coleta("Conforme");
                }
                if(naoconforme.isChecked()==true){
                    analise_laboratorial.setSituacao_coleta("Não Conforme");
                }
                if(conforme_nova_coleta.isChecked()==true){
                    analise_laboratorial.setSituacao_nova_coleta("Conforme");
                }
                if(naoconforme_nova_coleta.isChecked()==true){
                    analise_laboratorial.setSituacao_nova_coleta("Não Conforme");
                }
                analise_laboratorial.setNotificacao(notificacao.getText().toString());
                analise_laboratorial.setProduto(produto);
                bd.addAnaliseLaboratorial(analise_laboratorial);
                Toast.makeText(getActivity(), "Análise Laboratorial criada com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Analise_Laboratorial());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
        if(btnescolherproduto.getText().toString().equals("Clique para escolher o produto") || notificacao.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Preencha todos os campos solicitados!",
                    Toast.LENGTH_LONG).show();
            return false;
       }
      else{
            return true;
        }
    }

    public static Cadastro_Analise_Laboratorial newInstance(int id, int id_produto) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_produto", id_produto);

        Cadastro_Analise_Laboratorial fragment = new Cadastro_Analise_Laboratorial();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
            id_produto = bundle.getInt("id_produto");

        }
    }


}

