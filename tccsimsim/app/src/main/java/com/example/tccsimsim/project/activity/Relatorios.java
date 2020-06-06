package com.example.tccsimsim.project.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Relatorios extends Fragment implements View.OnClickListener {


    View minhaView;
    Button gerarelatorio;
    private BDSQLiteHelper bd;
    private ImageView imageView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_relatorios, container, false);
        bd = new BDSQLiteHelper(getActivity());
        gerarelatorio = (Button) minhaView.findViewById(R.id.button__Relatorios);
        imageView = (ImageView)minhaView.findViewById(R.id.imageView_StorageDownload);
        gerarelatorio.setOnClickListener(this);


        return minhaView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button__Relatorios:
                try {
                    gerarPDF();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //------------------------------------------GERAR PDF----------------------------------------------------------------


    private void gerarPDF() throws DocumentException, FileNotFoundException {



        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        String nome_Arquivo = diretorio.getPath()+"/"+"tccsimsim"+System.currentTimeMillis()+".pdf";


        File pdf = new File(nome_Arquivo);


        OutputStream outputStream =  new FileOutputStream(pdf);


        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document,outputStream);

        writer.setBoxSize("tccsimsim", new Rectangle(36,54,559,788));


        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA,20,Font.BOLD);

        Paragraph paragraph = new Paragraph("Curso Firebase Módulo II",font);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        Paragraph paragraph_2 = new Paragraph("Jone Arce",font);

        paragraph_2.setAlignment(Element.ALIGN_LEFT);


        ListItem item = new ListItem();

        item.add(paragraph);
        item.add(paragraph_2);



        document.add(item);




        document.close();

        visualizarPDF(pdf);


    }


    private void visualizarPDF(File pdf){


        PackageManager packageManager = getActivity().getPackageManager();

        Intent itent = new Intent(Intent.ACTION_VIEW);

        itent.setType("application/pdf");

        List<ResolveInfo> lista = packageManager.queryIntentActivities(itent,PackageManager.MATCH_DEFAULT_ONLY);


        if(lista.size() > 0 ){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri uri = FileProvider.getUriForFile(getActivity().getBaseContext(),getActivity().getBaseContext().getApplicationContext().getPackageName() + ".provider",pdf);

            intent.setDataAndType(uri,"application/pdf");

            startActivity(intent);

        }else{

            Toast.makeText(getActivity(), "Erro ao Abrir PDF! \n Não foi detectado nenhum leitor de PDF no seu Dispositivo.",
                    Toast.LENGTH_LONG).show();

        }



    }
}
