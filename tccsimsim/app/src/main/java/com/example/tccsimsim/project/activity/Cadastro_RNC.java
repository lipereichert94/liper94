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
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.RNC;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Cadastro_RNC extends Fragment implements View.OnClickListener {

    View minhaView;
    private Button btnescolherestabelecimento, btnsalvar, btnremover,dt_inspecao,dt_verificacao,btncancelar;
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

        btncancelar = (Button) minhaView.findViewById(R.id.button_CancelarRNC);
        btnsalvar = (Button) minhaView.findViewById(R.id.button_SalvarRNC);
        btnremover = (Button) minhaView.findViewById(R.id.button_removerRNC);
        dt_inspecao = (Button) minhaView.findViewById(R.id.button_data_inspecao_cadastro_RNC);
        dt_verificacao = (Button) minhaView.findViewById(R.id.btn_dtverificacao_RNC);
        descricao = (EditText)minhaView.findViewById(R.id.descricao_cadastro_RNC);
        rbconforme = (RadioButton)minhaView.findViewById(R.id.radioconforme);
        rbnaoconforme = (RadioButton)minhaView.findViewById(R.id.radionaoconforme);
        btnfoto = (Button) minhaView.findViewById(R.id.btnFoto);
        btngaleria = (Button) minhaView.findViewById(R.id.btn_Galeria);
        foto = (ImageButton) minhaView.findViewById(R.id.foto_RNC);
        btnescolherestabelecimento = (Button) minhaView.findViewById(R.id.button_EscolherEstabelecimento_cadastro_RNC);
        btnsalvar.setOnClickListener(this);
        dt_verificacao.setOnClickListener(this);
        dt_inspecao.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btnfoto.setOnClickListener(this);
        btngaleria.setOnClickListener(this);
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
            dt_verificacao.setText(rnc.getDt_verificacao());
            descricao.setText(rnc.getDescricao());
            if (rnc.getSituacao().equals("Conforme"))
                rbconforme.setChecked(true);
            else
                rbnaoconforme.setChecked(true);
            caminho_foto = rnc.getUrl_imagem();
            mostraFotoSelfie(caminho_foto);

        }

        // Pede permissão para acessar as mídias gravadas no dispositivo
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        // Pede permissão para escrever arquivos no dispositivo
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        return minhaView;
    }

    public void buscar(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGENS);
    }
    //Método para tirar foto
    public void tirarFoto(View view) {
        Log.d("----->", "CHEGOU NO METODO PARA TIRAR FOTO");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) !=null){
            try {
                arquivoFoto = criaArquivo();
            } catch (IOException ex) {
                mostraAlerta(getString(R.string.erro), getString(
                        R.string.erro_salvando_foto));
            }
            if (arquivoFoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getBaseContext(), getActivity().getBaseContext().getApplicationContext().getPackageName() + ".provider", arquivoFoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
                Log.d("----->", "ABRIU CAMERA");

            }
        }
    }
    //Método que cria arquivo
    private File criaArquivo() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_Hhmmss").format(new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagem = new File(pasta.getPath() + File.separator + "JPG_" + timeStamp + ".jpg");
        return imagem;
    }
    //Retorno da câmera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("----->", "ENTROUU ON RESULT FRAGMENTTTT ");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null,
                    null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            arquivoFoto = new File(picturePath);
            caminho_foto = arquivoFoto.getAbsolutePath();
            mostraFotoSelfie(arquivoFoto.getAbsolutePath());
        }
        if (resultCode == RESULT_OK && requestCode == CAMERA) {
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(arquivoFoto)));
            Log.d("----->", "CAMINHOFOTO: "+arquivoFoto.getAbsolutePath());
            caminho_foto = arquivoFoto.getAbsolutePath();
            if(caminho_foto!=null){
                mostraFotoSelfie(arquivoFoto.getAbsolutePath());
            }
        }
    }
    private void mostraFotoSelfie(String caminho) {
        // Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        foto.setImageBitmap(BitmapFactory.decodeFile(caminho));

    }
    private void mostraAlerta(String titulo, String mensagem) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
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
            case R.id.btn_dtverificacao_RNC:
                EscolherData();
                break;
            case R.id.btnFoto:
                tirarFoto(view);
                break;
            case R.id.btn_Galeria:
                buscar(view);
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
                    dt_verificacao.setText("0"+mDay+"-");
                }
                else{
                    dt_verificacao.setText(mDay+"-");
                }
                if(mMonth == 0 || mMonth == 1 || mMonth == 2 || mMonth == 3 || mMonth ==4 || mMonth ==5 || mMonth ==6 || mMonth ==7 || mMonth ==8 || mMonth ==9 ) {
                    dt_verificacao.setText(dt_verificacao.getText()+"0"+(mMonth+1)+"-"+myear);
                }
                else{
                    dt_verificacao.setText(""+dt_verificacao.getText()+(mMonth+1)+"-"+myear);
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
                rnc.setUrl_imagem(caminho_foto);
                mostraFotoSelfie(caminho_foto);
                rnc.setEstabelecimento(estabelecimento);
                bd.updateRNC(rnc);
                Toast.makeText(getActivity(), "RNC alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_RNC());
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
                rnc.setUrl_imagem(caminho_foto);
                bd.addRNC(rnc);
                Toast.makeText(getActivity(), "RNC criado com sucesso!",
                        Toast.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Lista_RNC());
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

