package com.example.tccsimsim.project.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.RNC;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Cadastro_AI extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherestabelecimento, btnsalvar, btnremover,dt_ai,btncancelar;
    private BDSQLiteHelper bd;
    private EditText infracao,penalidade,situacao;
    private int id = 0;
    private int id_estabelecimento = -1;
    DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        minhaView = inflater.inflate(R.layout.layout_cadastro_ai, container, false);
        bd = new BDSQLiteHelper(getActivity());

        btncancelar = (Button) minhaView.findViewById(R.id.button_CancelarAI);
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarAI);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerAI);
        dt_ai = (Button) minhaView.findViewById(R.id.button_data_cadastro_AI);
        infracao = (EditText)minhaView.findViewById(R.id.infracao_cadastro_AI);
        penalidade = (EditText)minhaView.findViewById(R.id.penalidade_cadastro_AI);
        situacao = (EditText)minhaView.findViewById(R.id.editText_situacao_cadastro_ai);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_AI);
        btnsalvar.setOnClickListener(this);
        btnremover.setOnClickListener(this);
        dt_ai.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btnescolherestabelecimento.setOnClickListener(this);
        setDataAtual();
        readBundle(getArguments());

        //verifica se é cadastro ou alteração
        if (id_estabelecimento != -1) {
            Estabelecimento estabelecimento = null;
            try {
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            btnescolherestabelecimento.setText(estabelecimento.getNome());

        }
        if (id != 0) {
            btnremover.setText("Remover");
            AI ai = null;
            try {
                ai = bd.getAI(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dt_ai.setText(ai.getDt_ai());
            infracao.setText(ai.getInfracao_ai());
            penalidade.setText(ai.getPenalidade_ai());
            situacao.setText(ai.getSituacao_ai());
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
            dt_ai.setText("0"+day+"-");
        }
        else{
            dt_ai.setText(day+"-");
        }
        if(month == 0 || month == 1 || month == 2 || month == 3 || month ==4 || month ==5 || month ==6 || month ==7 || month ==8 || month ==9 ) {
            dt_ai.setText(dt_ai.getText()+"0"+(month+1)+"-"+year);
        }
        else{
            dt_ai.setText(""+dt_ai.getText()+(month+1)+"-"+year);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_SalvarAI:
                try {
                    SalvarAI();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_EscolherEstabelecimento_cadastro_AI:
                EscolherEstabelecimento();
                break;
            case R.id.button_CancelarAI:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_AI());
                ft.commit();
                break;
            case R.id.button_removerAI:
                if (id != 0) {
                    RemoverAI();
                }
                break;
        }
    }

    private void EscolherEstabelecimento() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.conteudo_fragmento, new Lista_Escolher_Estabelecimento().newInstance(id,"AI",id));
        ft.commit();

    }

    private void RemoverAI() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.confirmar_exclusao)
                .setMessage(R.string.quer_mesmo_apagar)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        AI finalai = null;
                        try {
                            finalai = bd.getAI(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bd.deleteAI(finalai);
                        Toast.makeText(getActivity(), "AI Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Lista_AI());
                        ft.commit();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void SalvarAI() throws ParseException {
        if (id != 0) {
            if(verificacampos()) {
                //alterar
                Estabelecimento estabelecimento = new Estabelecimento();
                AI ai = new AI();
                ai.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                ai.setDt_ai(dt_ai.getText().toString());
                ai.setInfracao_ai(infracao.getText().toString());
                ai.setPenalidade_ai(penalidade.getText().toString());
                ai.setSituacao_ai(situacao.getText().toString());
                ai.setEstabelecimento(estabelecimento);
                bd.updateAI(ai);
                Toast.makeText(getActivity(), "AI alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_AI());
                ft.commit();
            }
        }
        //gravar novo RNC
        else {
            if(verificacampos()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                AI ai = new AI();
                ai.setId(id);
                estabelecimento = bd.getEstabelecimento(id_estabelecimento);
                ai.setDt_ai(dt_ai.getText().toString());
                ai.setInfracao_ai(infracao.getText().toString());
                ai.setPenalidade_ai(penalidade.getText().toString());
                ai.setSituacao_ai(situacao.getText().toString());
                ai.setEstabelecimento(estabelecimento);
                bd.addAI(ai);
                Toast.makeText(getActivity(), "AI criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_AI());
                ft.commit();
            }
        }
    }

    private boolean verificacampos() {
        if(btnescolherestabelecimento.getText().toString().equals("Clique para escolher estabelecimento") || infracao.getText().toString().equals("") || penalidade.getText().toString().equals("") || situacao.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Preencha todos os campos solicitados!",
                    Toast.LENGTH_LONG).show();
            return false;
       }
      else{
            return true;
        }
    }

    public static Cadastro_AI newInstance(int id, int id_estabelecimento) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("id_estabelecimento", id_estabelecimento);

        Cadastro_AI fragment = new Cadastro_AI();
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

