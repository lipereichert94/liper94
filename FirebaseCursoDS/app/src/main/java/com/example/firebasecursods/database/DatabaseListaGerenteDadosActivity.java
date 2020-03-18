package com.example.firebasecursods.database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecursods.R;
import com.example.firebasecursods.Util.DialogAlerta;
import com.example.firebasecursods.Util.DialogProgress;
import com.example.firebasecursods.Util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class DatabaseListaGerenteDadosActivity extends AppCompatActivity implements View.OnClickListener  {


    private EditText editText_Nome;
    private EditText editText_Idade;

    private Button button_Alterar;
    private Button button_Remover;


    private Gerente gerenteSelecionado;

    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private boolean firebaseOffline = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_lista_gerente_dados_activity);

        editText_Nome = (EditText)findViewById(R.id.editText_Database_Dados_Gerente_Nome);
        editText_Idade = (EditText)findViewById(R.id.editText_Database_Dados_Gerente_Idade);


        button_Alterar = (Button)findViewById(R.id.button_Database_Dados_Gerente_Alterar);
        button_Remover = (Button)findViewById(R.id.button_Database_Dados_Gerente_Remover);

        button_Alterar.setOnClickListener(this);
        button_Remover.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        gerenteSelecionado = getIntent().getParcelableExtra("gerente");

        ativarFirebaseOffline();
        carregarDadosGerente();


    }


    //--------------------------------------CARREGAR DADOS FUNCIONARIO ---------------------------------------

    private void ativarFirebaseOffline(){


        try{
            if(!firebaseOffline){

                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

                firebaseOffline = true;

            }else{

                //firebase ja estiver funcionando offline
            }


        }catch(Exception e){
            //erro
        }
    }
    private void carregarDadosGerente(){


        editText_Nome.setText(gerenteSelecionado.getNome());
        editText_Idade.setText(gerenteSelecionado.getIdade()+"");



    }


    //--------------------------------------TRATAMENTO DE CLICKS ---------------------------------------
    @Override
    public void onClick(View v) {


        switch (v.getId()){


            case R.id.button_Database_Dados_Gerente_Alterar:


                buttonAlterar();

                break;


            case R.id.button_Database_Dados_Gerente_Remover:

                removerFuncionario();

                break;

        }
    }

    private void buttonAlterar(){



        String nome = editText_Nome.getText().toString();
        String idade_String = editText_Idade.getText().toString();




            int idade = Integer.parseInt(idade_String);






                    alterarDados(nome,idade);





    }



    private void alterarDados(String nome, int idade){


        //final DialogProgress progress = new DialogProgress();
       // progress.show(getSupportFragmentManager(),"1");


        DatabaseReference reference = database.getReference().child("BD").child("Gerentes");



        Gerente gerente = new Gerente(nome,idade);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(gerenteSelecionado.getId(),gerente);


        reference.updateChildren(atualizacao).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){


                   // progress.dismiss();
                    Toast.makeText(getBaseContext(),"Sucesso ao Alterar Dados",Toast.LENGTH_LONG).show();
                    finish();

                }else{

                   // progress.dismiss();
                    Toast.makeText(getBaseContext(),"Erro ao Alterar Dados",Toast.LENGTH_LONG).show();

                }


            }
        });


    }


    private void removerFuncionario(){


        DatabaseReference reference = database.getReference().child("BD").child("Gerentes");



        reference.child(gerenteSelecionado.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Funcionario",Toast.LENGTH_LONG).show();
                    finish();

                }else{

                    Toast.makeText(getBaseContext(),"Erro ao Remover Funcionario",Toast.LENGTH_LONG).show();

                }


            }
        });


    }
}

