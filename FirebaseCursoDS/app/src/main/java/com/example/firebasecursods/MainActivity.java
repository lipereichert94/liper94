package com.example.firebasecursods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private CardView cardView_Storage_Download;
    private CardView cardView_Storage_Upload;
    private CardView cardView_Database_LerDados;
    private CardView cardView_Database_GravarAlterarExcluir;
    private CardView cardView_Empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_Storage_Download = (CardView)findViewById(R.id.cardView_Storage_Download);
        cardView_Database_GravarAlterarExcluir = (CardView)findViewById(R.id.cardView_Database_GravarAlterarExcluirDados);
        cardView_Database_LerDados = (CardView)findViewById(R.id.cardView_Database_LerDados);
        cardView_Storage_Upload = (CardView)findViewById(R.id.cardView_Storage_Upload);
        cardView_Empresas = (CardView)findViewById(R.id.cardView_Empresas);

        cardView_Empresas.setOnClickListener(this);
        cardView_Storage_Download.setOnClickListener(this);
        cardView_Database_GravarAlterarExcluir.setOnClickListener(this);
        cardView_Storage_Upload.setOnClickListener(this);
        cardView_Database_LerDados.setOnClickListener(this);
    }

    //---------------------------------------------Tratamento de Clicks------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cardView_Storage_Download:
                //ir para activity de download

                break;

            case R.id.cardView_Storage_Upload:
                //ir para activity de upload

                break;

            case R.id.cardView_Database_LerDados:
                //ir para activity de lerdados

                break;

            case R.id.cardView_Database_GravarAlterarExcluirDados:
                //ir para activity de gravar alterar excluir

                break;

            case R.id.cardView_Empresas:
                //ir para activity de empresas

                break;


        }
    }
}
