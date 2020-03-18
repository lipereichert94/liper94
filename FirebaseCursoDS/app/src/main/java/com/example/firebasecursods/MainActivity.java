package com.example.firebasecursods;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.firebasecursods.Util.Permissao;
import com.example.firebasecursods.database.DatabaseGravarAlterarRemoverActivity;
import com.example.firebasecursods.database.DatabaseLerDadosActivity;
import com.example.firebasecursods.database_lista__funcionario_offline.DatabaseListaFuncionarioActivityOffLine;
import com.example.firebasecursods.database_lista_empresa.DatabaseListaEmpresaActivity;
import com.example.firebasecursods.storage.StorageDownloadActivity;
import com.example.firebasecursods.storage.StorageUploadActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private CardView cardView_Storage_Download;
    private CardView cardView_Storage_Upload;
    private CardView cardView_Database_LerDados;
    private CardView cardView_Database_GravarAlterarExcluir;
    private CardView cardView_Empresas;
    private CardView CardView_Empresas_Offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_Storage_Download = (CardView)findViewById(R.id.cardView_Storage_Download);
        cardView_Database_GravarAlterarExcluir = (CardView)findViewById(R.id.cardView_Database_GravarAlterarExcluirDados);
        cardView_Database_LerDados = (CardView)findViewById(R.id.cardView_Database_LerDados);
        cardView_Storage_Upload = (CardView)findViewById(R.id.cardView_Storage_Upload);
        cardView_Empresas = (CardView)findViewById(R.id.cardView_Empresas);
        CardView_Empresas_Offline = (CardView)findViewById(R.id.cardView_Empresas_ModoOffline);

        cardView_Empresas.setOnClickListener(this);
        cardView_Storage_Download.setOnClickListener(this);
        cardView_Database_GravarAlterarExcluir.setOnClickListener(this);
        cardView_Storage_Upload.setOnClickListener(this);
        cardView_Database_LerDados.setOnClickListener(this);
        CardView_Empresas_Offline.setOnClickListener(this);

        permissao();
    }

    //---------------------------------------------Tratamento de Clicks------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cardView_Storage_Download:
                //ir para activity de download
                Intent intent = new Intent(getBaseContext(), StorageDownloadActivity.class);

                startActivity(intent);
                break;

            case R.id.cardView_Storage_Upload:
                //ir para activity de upload
                Intent intent4 = new Intent(getBaseContext(), StorageUploadActivity.class);

                startActivity(intent4);
                break;

            case R.id.cardView_Database_LerDados:
                //ir para activity de lerdados
                Intent intent2 = new Intent(getBaseContext(), DatabaseLerDadosActivity.class);

                startActivity(intent2);
                break;

            case R.id.cardView_Database_GravarAlterarExcluirDados:
                //ir para activity de gravar alterar excluir
                Intent intent3 = new Intent(getBaseContext(), DatabaseGravarAlterarRemoverActivity.class);

                startActivity(intent3);

                break;

            case R.id.cardView_Empresas:
                //ir para activity de empresas
                Intent intent5 = new Intent(getBaseContext(), DatabaseListaEmpresaActivity.class);

                startActivity(intent5);
                break;
            case R.id.cardView_Empresas_ModoOffline:
                //ir para activity de empresas
                Intent intent6 = new Intent(getBaseContext(), DatabaseListaFuncionarioActivityOffLine.class);

                startActivity(intent6);
                break;

        }
    }

    //---------------------------------------------Permissão do usuario------------------------------------------------------------

    private void permissao(){


        String permissoes [] = new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,


        };


        Permissao.permissao(this,0,permissoes);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        for(int result: grantResults){

            if(result == PackageManager.PERMISSION_DENIED){

                Toast.makeText(this,"Aceite as permissões para o aplicativo funcionar corretamente",Toast.LENGTH_LONG).show();
                finish();

                break;
            }

        }


    }

}
