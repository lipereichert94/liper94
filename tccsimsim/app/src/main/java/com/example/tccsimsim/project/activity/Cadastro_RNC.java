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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.RNC;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_RNC extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherestabelecimento, btnsalvar, btnremover,dt_inspecao,dt_verificacao;
    private BDSQLiteHelper bd;
    private EditText descricao;
    private RadioButton rbconforme,rbnaoconforme;
    private int id = 0;
    private int id_estabelecimento = -1;
    private final int PERMISSAO_REQUEST = 2;
    private final int GALERIA_IMAGENS = 1;
    private final int CAMERA = 3;
    private File arquivoFoto = null;
    private ImageButton foto;
    private Button btnfoto;
    private Button btngaleria;
    private String caminho_foto = null;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_rnc, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarRNC);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerRNC);
        dt_inspecao = (Button) minhaView.findViewById(R.id.button_data_inspecao_cadastro_RNC);
        dt_verificacao = (Button) minhaView.findViewById(R.id.btn_dtverificacao_RNC);
        descricao = (EditText)minhaView.findViewById(R.id.descricao_cadastro_RNC);
        rbconforme = (RadioButton)minhaView.findViewById(R.id.radioconforme);
        rbnaoconforme = (RadioButton)minhaView.findViewById(R.id.radionaoconforme);
        foto = (ImageButton) minhaView.findViewById(R.id.foto_RNC);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_RNC);
        btnsalvar.setOnClickListener(this);
        dt_verificacao.setOnClickListener(this);
        dt_inspecao.setOnClickListener(this);
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
            RNC rnc = null;
            try {
                rnc = bd.getRNC(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dt_inspecao.setText(rnc.getDt_inspecao());
            dt_verificacao.setText(rnc.getDt_inspecao());
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
            dt_inspecao.setText("0"+day+"-");
        }
        else{
            dt_inspecao.setText(day+"-");
        }
        if(month == 0 || month == 1 || month == 2 || month == 3 || month ==4 || month ==5 || month ==6 || month ==7 || month ==8 || month ==9 ) {
            dt_inspecao.setText(dt_inspecao.getText()+"0"+(month+1)+"-"+year);
        }
        else{
            dt_inspecao.setText(""+dt_inspecao.getText()+(month+1)+"-"+year);
        }
        dt_inspecao.setText(day + "-" + (month + 1) + "-" + year);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarRNC:
                try {
                    SalvarRNC();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_EscolherEstabelecimento_cadastro_RNC:
                EscolherEstabelecimento();
                break;
            case R.id.button_data_inspecao_cadastro_RNC:
                EscolherData();
                break;
            case R.id.button_removerRNC:
                if (id != 0) {
                    RemoverRNC();
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
                    dt_inspecao.setText("0"+mDay+"-");
                }
                else{
                    dt_inspecao.setText(mDay+"-");
                }
                if(mMonth == 0 || mMonth == 1 || mMonth == 2 || mMonth == 3 || mMonth ==4 || mMonth ==5 || mMonth ==6 || mMonth ==7 || mMonth ==8 || mMonth ==9 ) {
                    dt_inspecao.setText(dt_inspecao.getText()+"0"+(mMonth+1)+"-"+myear);
                }
                else{
                    dt_inspecao.setText(""+dt_inspecao.getText()+(mMonth+1)+"-"+myear);
                }
            }
        }, year,month,day);
        dpd.show();
    }

    private void EscolherEstabelecimento() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.d("----->", "No frame escolher estabelecimento chegou passando id do produto ="+id);

        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Estabelecimento().newInstance(id,"RNC",id));
        ft.commit();

    }

    private void RemoverRNC() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        RNC finalrnc = null;
                        try {
                            finalrnc = bd.getRNC(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bd.deleteRNC(finalrnc);
                        Toast.makeText(getActivity(), "RNC Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarRNC() throws ParseException {
        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Estabelecimento estabelecimento = new Estabelecimento();
                RNC rnc = new RNC();
                rnc.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                rnc.setDt_inspecao(dt_inspecao.getText().toString());
                rnc.setDt_verificacao(dt_verificacao.getText().toString());
                rnc.setDescricao(descricao.getText().toString());
                if(rbconforme.isChecked()==true){
                    rnc.setSituacao("Conforme");
                }
                if(rbnaoconforme.isChecked()==true){
                    rnc.setSituacao("Não Conforme");
                }
                rnc.setEstabelecimento(estabelecimento);
                bd.updateRNC(rnc);
                Toast.makeText(getActivity(), "RNC alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                ft.commit();
            }
        }
        //gravar novo RNC
        else {
            if(verificacampos()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                RNC rnc = new RNC();
                rnc.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                rnc.setDt_inspecao(dt_inspecao.getText().toString());
                rnc.setDt_verificacao(dt_verificacao.getText().toString());
                rnc.setDescricao(descricao.getText().toString());
                if(rbconforme.isChecked()==true){
                    rnc.setSituacao("Conforme");
                }
                if(rbnaoconforme.isChecked()==true){
                    rnc.setSituacao("Não Conforme");
                }
                rnc.setEstabelecimento(estabelecimento);
                bd.addRNC(rnc);
                Toast.makeText(getActivity(), "RNC criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
     //   if(btnescolherestabelecimento.getText().toString().equals("Clique para escolher estabelecimento") || btnescolherdata.getText().toString().equals("Clique para escolher a data")){
    //        Toast.makeText(getActivity(), "Escolha os campos solicitados!",
     //               Toast.LENGTH_LONG).show();
    //        return false;
     //   }
      //  else{
            return true;
   //     }
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
    public static Cadastro_RNC newInstance(int id, int id_estabelecimento) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_estabelecimento", id_estabelecimento);

        Cadastro_RNC fragment = new Cadastro_RNC();
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

