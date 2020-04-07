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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Licenca_Ambiental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_Licenca_Ambiental extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherestabelecimento, btnsalvar, btnremover, btnescolherdata,dt_registro;
    private BDSQLiteHelper bd;
    private int id = 0;
    private int id_estabelecimento = -1;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_licenca_ambiental, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarLicencaAmbiental);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerLicencaAmbiental);
        dt_registro = (Button) minhaView.findViewById(R.id.btn_dtregistro_licenca_ambiental);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_licenca_ambiental);
        btnescolherdata = (Button) minhaView.findViewById(R.id.button_EscolherData_cadastro_licenca_ambiental);
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
            Licenca_Ambiental licenca_ambiental = bd.getLicencaAmbiental(id);
            dt_registro.setText(licenca_ambiental.getDt_registro());
            btnescolherdata.setText(licenca_ambiental.getDt_validade());
        }
        return minhaView;
    }

    private void setDataAtual() {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        dt_registro.setText(day + "/" + (month + 1) + "/" + year);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarLicencaAmbiental:
                SalvarLicencaAmbiental();
                break;
            case R.id.button_EscolherEstabelecimento_cadastro_licenca_ambiental:
                EscolherEstabelecimento();
                break;
            case R.id.button_EscolherData_cadastro_licenca_ambiental:
                EscolherData();
                break;
            case R.id.button_removerLicencaAmbiental:
                if (id != 0) {
                    RemoverLicencaAmbiental();
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
                btnescolherdata.setText(mDay + "/" + (mMonth + 1) + "/" + myear);
            }
        }, year,month,day);
        dpd.show();
    }

    private void EscolherEstabelecimento() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Estabelecimento().newInstance(id,"licenca_ambiental",id));
        ft.commit();

    }

    private void RemoverLicencaAmbiental() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Licenca_Ambiental finallicenca_ambiental = bd.getLicencaAmbiental(id);
                        bd.deleteLicencaAmbiental(finallicenca_ambiental);
                        Toast.makeText(getActivity(), "Licença Ambiental excluída com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Licenca_Ambiental());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarLicencaAmbiental() {
        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Estabelecimento estabelecimento = new Estabelecimento();
                Licenca_Ambiental licenca_ambiental = new Licenca_Ambiental();
                licenca_ambiental.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                licenca_ambiental.setDt_validade(btnescolherdata.getText().toString());
                licenca_ambiental.setDt_registro(dt_registro.getText().toString());
                licenca_ambiental.setEstabelecimento(estabelecimento);
                bd.updateLicencaAmbiental(licenca_ambiental);
                Toast.makeText(getActivity(), "Licença Ambiental alterada com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Licenca_Ambiental());
                ft.commit();
            }
        }
        //gravar novo atestado saude
        else {
            if(verificacampos()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                Licenca_Ambiental licenca_ambiental = new Licenca_Ambiental();
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                licenca_ambiental.setDt_validade(btnescolherdata.getText().toString());
                licenca_ambiental.setDt_registro(dt_registro.getText().toString());
                licenca_ambiental.setEstabelecimento(estabelecimento);
                bd.addLicencaAmbiental(licenca_ambiental);
                Toast.makeText(getActivity(), "Licença Ambiental criadacom sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Licenca_Ambiental());
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
    public static Cadastro_Licenca_Ambiental newInstance(int id, int id_estabelecimento) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_estabelecimento", id_estabelecimento);

        Cadastro_Licenca_Ambiental fragment = new Cadastro_Licenca_Ambiental();
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

