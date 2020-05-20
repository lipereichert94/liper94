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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_Atestado_Saude extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherestabelecimento, btnsalvar, btnremover, btnescolherdata,dt_registro;
    private BDSQLiteHelper bd;
    private int id = 0;
    private int id_estabelecimento = -1;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_atestado_saude, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarAtestadoSaude);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerAtestadoSaude);
        dt_registro = (Button) minhaView.findViewById(R.id.btn_dtregistro_atestadosaude);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_atestado_saude);
        btnescolherdata = (Button) minhaView.findViewById(R.id.button_EscolherData_cadastro_atestado_saude);
        btnsalvar.setOnClickListener(this);
        btnescolherdata.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        btnescolherestabelecimento.setOnClickListener(this);
        setDataAtual();
        readBundle(getArguments());
        //verifica se é cadastro ou alteração
        if (id_estabelecimento != -1) {
            Estabelecimento estabelecimento = bd.getEstabelecimento(id_estabelecimento);
            btnescolherestabelecimento.setText(estabelecimento.getNome());
        }
        if (id != 0) {
            btnremover.setText("Remover");
            Atestado_Saude atestado_saude = null;
            try {
                atestado_saude = bd.getAtestadoSaude(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dt_registro.setText(atestado_saude.getDt_registro());
            btnescolherdata.setText(atestado_saude.getDt_validade());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarAtestadoSaude:
                try {
                    SalvarAtestadoSaude();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_EscolherEstabelecimento_cadastro_atestado_saude:
                EscolherEstabelecimento();
                break;
            case R.id.button_EscolherData_cadastro_atestado_saude:
                EscolherData();
                break;
            case R.id.button_removerAtestadoSaude:
                if (id != 0) {
                    RemoverAtestadoSaude();
                }
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

    private void EscolherEstabelecimento() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.d("----->", "No frame escolher estabelecimento chegou passando id do produto ="+id);

        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Estabelecimento().newInstance(id,"atestado_saude",id));
        ft.commit();

    }

    private void RemoverAtestadoSaude() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Atestado_Saude finalatestatosaude = null;
                        try {
                            finalatestatosaude = bd.getAtestadoSaude(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bd.deleteAtestadoSaude(finalatestatosaude);
                        Toast.makeText(getActivity(), "Atestado de Saúde Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarAtestadoSaude() throws ParseException {
        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Estabelecimento estabelecimento = new Estabelecimento();
                Atestado_Saude atestado_saude = new Atestado_Saude();
                atestado_saude.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                atestado_saude.setDt_validade(btnescolherdata.getText().toString());
                atestado_saude.setDt_registro(dt_registro.getText().toString());
                atestado_saude.setEstabelecimento(estabelecimento);
                bd.updateAtestadoSaude(atestado_saude);
                Toast.makeText(getActivity(), "Atestado de Saúde alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                ft.commit();
            }
        }
        //gravar novo atestado saude
        else {
            if(verificacampos()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                Atestado_Saude atestado_saude = new Atestado_Saude();
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                atestado_saude.setDt_validade(btnescolherdata.getText().toString());
                atestado_saude.setDt_registro(dt_registro.getText().toString());
                atestado_saude.setEstabelecimento(estabelecimento);
                bd.addAtestadoSaude(atestado_saude);
                Toast.makeText(getActivity(), "Atestado de Saúde criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
        if(btnescolherestabelecimento.getText().toString().equals("Clique para escolher estabelecimento") || btnescolherdata.getText().toString().equals("Clique para escolher a data")){
            Toast.makeText(getActivity(), "Escolha os campos solicitados!",
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
    public static Cadastro_Atestado_Saude newInstance(int id, int id_estabelecimento) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_estabelecimento", id_estabelecimento);

        Cadastro_Atestado_Saude fragment = new Cadastro_Atestado_Saude();
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

