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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;
import com.example.tccsimsim.project.model.Produto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_Media_Mensal extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherproduto, btnsalvar, btnremover, btnescolherdata,btncancelar;
    private BDSQLiteHelper bd;
    private int id = 0;
    private int id_produto = -1;
    private EditText quantidade;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_media_mensal_produto, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btncancelar = (Button) minhaView.findViewById(R.id.button_CancelarMediaMensal);
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarMediaMensal);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerMediaMensal);
        btnescolherproduto = (Button) minhaView.findViewById(R.id.button_EscolherProduto_cadastro_media_mensal);
        btnescolherdata = (Button) minhaView.findViewById(R.id.button_EscolherData_cadastro_media_mensal);
        quantidade = (EditText) minhaView.findViewById(R.id.editText_QuantidadeCadastroMediaMensal);
        btnsalvar.setOnClickListener(this);
        btnescolherdata.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        btnescolherproduto.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        setDataAtual();
        readBundle(getArguments());
        //verifica se é cadastro ou alteração
        if (id_produto != -1) {
            Produto produto = bd.getProduto(id_produto);
            Estabelecimento estabelecimento = bd.getEstabelecimento(produto.getEstabelecimento().getId());
            btnescolherproduto.setText("Estabelecimento: "+estabelecimento.getNome()+" Produto:"+produto.getNome());
        }
        if (id != 0) {
            btnremover.setText("Remover");
            Media_Mensal media_mensal = null;
            try {
                media_mensal = bd.getMediaMensal(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            btnescolherdata.setText(media_mensal.getDt_media_mensal());
        quantidade.setText(""+media_mensal.getQuantidade());
        }
        return minhaView;
    }

    private void setDataAtual() {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarMediaMensal:
                try {
                    SalvarMediaMensal();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_EscolherProduto_cadastro_media_mensal:
                EscolherProduto();
                break;
            case R.id.button_EscolherData_cadastro_media_mensal:
                EscolherData();
                break;
            case R.id.button_removerMediaMensal:
                if (id != 0) {
                    RemoverMediaMensal();
                }
                break;
            case R.id.button_CancelarMediaMensal:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Media_Mensal());
                ft.commit();
                break;
        }
    }

    private void EscolherData() {

        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);


        dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int myear, int mMonth, int mDay) {
                if(mDay == 1 || mDay == 2 || mDay == 3 || mDay ==4 || mDay ==5 || mDay ==6 || mDay ==7 || mDay ==8 || mDay ==9 ){
                    btnescolherdata.setText("0"+mDay+"-");
                }
                else{
                    btnescolherdata.setText(mDay+"-");
                }
                if(mMonth == 0 || mMonth == 1 || mMonth == 2 || mMonth == 3 || mMonth ==4 || mMonth ==5 || mMonth ==6 || mMonth ==7 || mMonth ==8 || mMonth ==9 ) {
                    btnescolherdata.setText(btnescolherdata.getText()+"0"+(mMonth+1)+"-"+myear);
                }
                else{
                    btnescolherdata.setText(""+btnescolherdata.getText()+(mMonth+1)+"-"+myear);
                }
            }
        }, year,month,day);
        dpd.show();
    }

    private void EscolherProduto() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Produto().newInstance(id,"media_mensal",id));
        ft.commit();

    }

    private void RemoverMediaMensal() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Media_Mensal finalmedia_mensal = null;
                        try {
                            finalmedia_mensal = bd.getMediaMensal(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bd.deleteMediaMensal(finalmedia_mensal);
                        Toast.makeText(getActivity(), "Média Mensal excluída com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Media_Mensal());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarMediaMensal() throws ParseException {

        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Produto produto = new Produto();
                Media_Mensal media_mensal = new Media_Mensal();
                media_mensal.setId(id);
                produto = bd.getProduto(id_produto);
                media_mensal.setDt_media_mensal(btnescolherdata.getText().toString());
                media_mensal.setProduto(produto);
                media_mensal.setQuantidade(new Integer(quantidade.getText().toString()));
                bd.updateMediaMensal(media_mensal);
                Toast.makeText(getActivity(), "Media Mensal alterada com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Media_Mensal());
                ft.commit();
            }
        }
        //gravar novo atestado saude
        else {
            if(verificacampos()) {
                Produto produto = new Produto();
                Media_Mensal media_mensal = new Media_Mensal();
                produto = bd.getProduto(id_produto);
                media_mensal.setDt_media_mensal(btnescolherdata.getText().toString());
                media_mensal.setProduto(produto);
                media_mensal.setQuantidade(new Integer(quantidade.getText().toString()));
                bd.addMediaMensal(media_mensal);
                Toast.makeText(getActivity(), "Media Mensal criada com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Media_Mensal());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
        if(btnescolherproduto.getText().toString().equals("Clique para escolher produto") || btnescolherdata.getText().toString().equals("Clique para escolher a data") || quantidade.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Preencha os campos solicitados!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    private Date formataStringtoDate(String string) {
        Date dt = new Date();
        Log.d("----->", "Formatar data "+string);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            dt = formatter.parse(string);
            Log.d("----->", "Formatada data "+dt.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    public static Cadastro_Media_Mensal newInstance(int id, int id_produto) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_produto", id_produto);

        Cadastro_Media_Mensal fragment = new Cadastro_Media_Mensal();
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

