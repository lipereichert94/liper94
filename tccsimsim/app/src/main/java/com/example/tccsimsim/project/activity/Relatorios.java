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
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Analise_Laboratorial;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;
import com.example.tccsimsim.project.model.Produto;
import com.example.tccsimsim.project.model.RNC;
import com.itextpdf.text.Chunk;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

public class Relatorios extends Fragment implements View.OnClickListener {


    View minhaView;
    Button gerarelatorio;
    private BDSQLiteHelper bd;
    private ImageView imageView;
    private final int PERMISSAO_REQUEST = 2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_relatorios, container, false);
        bd = new BDSQLiteHelper(getActivity());
        gerarelatorio = (Button) minhaView.findViewById(R.id.button__Relatorios);
        imageView = (ImageView)minhaView.findViewById(R.id.imageView_StorageDownload);
        gerarelatorio.setOnClickListener(this);
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

       // writer.setBoxSize("tccsimsim", new Rectangle(36,54,559,788));


        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA,30,Font.BOLD);
        Font font_2 = new Font(Font.FontFamily.HELVETICA,20,Font.BOLD);

        Paragraph paragraph = new Paragraph("DOCUMENTO PARA AUDITORIA ESTADUAL",font);

        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(10);
        Paragraph paragraph_2 = new Paragraph("Estabelecimentos cadastrados:",font_2);
        paragraph_2.setAlignment(Element.ALIGN_LEFT);
        paragraph_2.setSpacingAfter(10);
        document.add(paragraph);
        document.add(paragraph_2);

        ListItem item = new ListItem();

        try {
            List<Estabelecimento> listaestabelecimentos = bd.getAllEstabelecimentos();
            Estabelecimento estabelecimento;
            for(int i = 0; i<listaestabelecimentos.size();i++){
                estabelecimento = listaestabelecimentos.get(i);
                item.add("Nome: " +estabelecimento.getNome()+"\n");
                item.add("Nome fantasia: "+estabelecimento.getNome_fantasia()+"\n");
                item.add("Classificação: "+estabelecimento.getClassificacao()+"\n");
                item.add("CNPJ: "+estabelecimento.getCnpj()+"\n");
                item.add("Inscrição Estadual: "+estabelecimento.getInscricao_estadual()+"\n");
                item.add("Inscrição Municipal: "+estabelecimento.getInscricao_municipal()+"\n");
                item.add("Endereço: "+estabelecimento.getEndereco()+"\n");
                item.add("Endereço eletrônico: "+estabelecimento.getEndereco_eletronico()+"\n");
                item.add("Data de registro: "+estabelecimento.getDt_registro()+"\n");
                item.add("\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_3 = new Paragraph("Produtos cadastrados:",font_2);
        paragraph_3.setAlignment(Element.ALIGN_LEFT);
        paragraph_3.setSpacingAfter(10);
        document.add(paragraph_3);
        try {
            List<Produto> listaprodutos = bd.getAllProduto();
            Produto produto;
            for(int i = 0; i<listaprodutos.size();i++){
                produto = listaprodutos.get(i);
                item.add("Nome: " +produto.getNome()+"\n");
                item.add("Estabelecimento: "+produto.getEstabelecimento().getNome());
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_4 = new Paragraph("Média mensal cadastradas:",font_2);
        paragraph_4.setAlignment(Element.ALIGN_LEFT);
        paragraph_4.setSpacingAfter(10);
        document.add(paragraph_4);
        try {
            List<Media_Mensal> listamediasmensais = bd.getAllMediaMensal();
            Media_Mensal media_mensal;
            for(int i = 0; i<listamediasmensais.size();i++){
                media_mensal = listamediasmensais.get(i);
                item.add("Estabelecimento: " +media_mensal.getProduto().getEstabelecimento().getNome()+"\n");
                item.add("Nome do Produto: " +media_mensal.getProduto().getNome()+"\n");
                item.add("Quantidade: "+media_mensal.getQuantidade()+"\n");
                item.add("Data mensal da produção: "+media_mensal.getDt_media_mensal());
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_5 = new Paragraph("Análises Laboratoriais:",font_2);
        paragraph_5.setAlignment(Element.ALIGN_LEFT);
        paragraph_5.setSpacingAfter(10);
        document.add(paragraph_5);
        try {
            List<Analise_Laboratorial> listaanaliseslaboratoriais = bd.getAllAnaliseLaboratorial();
            Analise_Laboratorial analise_laboratorial;
            for(int i = 0; i<listaanaliseslaboratoriais.size();i++){
                analise_laboratorial = listaanaliseslaboratoriais.get(i);
                item.add("Estabelecimento: " +analise_laboratorial.getProduto().getEstabelecimento().getNome()+"\n");
                item.add("Nome do Produto: " +analise_laboratorial.getProduto().getNome()+"\n");
                item.add("Tipo da Análise: "+analise_laboratorial.getTipo()+"\n");
                item.add("Data da Coleta: "+analise_laboratorial.getDt_coleta()+"\n");
                item.add("Situação: "+analise_laboratorial.getSituacao_coleta()+"\n");
                item.add("Notificação: "+analise_laboratorial.getNotificacao()+"\n");
                item.add("Data da Nova Coleta: "+analise_laboratorial.getDt_nova_coleta()+"\n");
                item.add("Situação da Nova Coleta: "+analise_laboratorial.getDt_nova_coleta());
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_7 = new Paragraph("Autos de Infração:",font_2);
        paragraph_7.setAlignment(Element.ALIGN_LEFT);
        paragraph_7.setSpacingAfter(10);
        document.add(paragraph_7);
        try {
            List<AI> listaai = bd.getAllAI();
            AI ai;
            for(int i = 0; i<listaai.size();i++){
                ai = listaai.get(i);
                item.add("Estabelecimento: " +ai.getEstabelecimento().getNome()+"\n");
                item.add("Data: " +ai.getDt_ai()+"\n");
                item.add("Infração: "+ai.getInfracao_ai()+"\n");
                item.add("Penalidade: "+ai.getPenalidade_ai()+"\n");
                item.add("Situação: "+ai.getSituacao_ai());
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_8 = new Paragraph("Atestado de Saúde:",font_2);
        paragraph_8.setAlignment(Element.ALIGN_LEFT);
        paragraph_8.setSpacingAfter(10);
        document.add(paragraph_8);
        try {
            List<Atestado_Saude> listaatestadodesaude = bd.getAllAtestadoSaude();
            Atestado_Saude atestado_saude;
            for(int i = 0; i<listaatestadodesaude.size();i++){
                atestado_saude = listaatestadodesaude.get(i);
                item.add("Estabelecimento: " +atestado_saude.getEstabelecimento().getNome()+"\n");
                item.add("Data de Validade: " +atestado_saude.getDt_validade()+"\n");
                item.add("Data de registro: "+atestado_saude.getDt_registro()+"\n");
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_9 = new Paragraph("Licença Ambiental:",font_2);
        paragraph_9.setAlignment(Element.ALIGN_LEFT);
        paragraph_9.setSpacingAfter(10);
        document.add(paragraph_9);
        try {
            List<Licenca_Ambiental> listalicencaambiental = bd.getAllLicencaAmbiental();
            Licenca_Ambiental licenca_ambiental;
            for(int i = 0; i<listalicencaambiental.size();i++){
                licenca_ambiental = listalicencaambiental.get(i);
                item.add("Estabelecimento: " +licenca_ambiental.getEstabelecimento().getNome()+"\n");
                item.add("Data de Validade: " +licenca_ambiental.getDt_validade()+"\n");
                item.add("Data de registro: "+licenca_ambiental.getDt_registro()+"\n");
                item.add("\n"+"\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.add(item);
        item.clear();

        Paragraph paragraph_6 = new Paragraph("Relatórios de não conformidade:",font_2);
        paragraph_6.setAlignment(Element.ALIGN_LEFT);
        paragraph_6.setSpacingAfter(10);
        document.add(paragraph_6);

        try {
            List<RNC> listarnc = bd.getAllRNC();
            RNC rnc;
            for(int i = 0; i<listarnc.size();i++){
                rnc = listarnc.get(i);
                item.add("Estabelecimento: " +rnc.getEstabelecimento().getNome()+"\n");
                item.add("Data da inspeção: " +rnc.getDt_inspecao()+"\n");
                item.add("Descrição: " +rnc.getDescricao()+"\n");
                item.add("Data da verificação: " +rnc.getDt_verificacao()+"\n");
                item.add("Situação: " +rnc.getSituacao()+"\n");
                item.add("Imagem: "+"\n"+"\n");
                Image image = Image.getInstance(rnc.getUrl_imagem());
                image.scaleAbsolute(200f,300f);
                image.setAlignment(Element.ALIGN_CENTER);
                item.add(new Chunk(image,0,0,true));
                item.add("\n"+"\n");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
