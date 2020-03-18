package com.example.firebasecursods.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firebasecursods.R;
import com.example.firebasecursods.Util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseListaGerenteActivity extends AppCompatActivity implements RecyclerView_ListaGerente.ClickGerente,Runnable, View.OnClickListener {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;

    private RecyclerView_ListaGerente recyclerView_listaGerente;
    private List<Gerente> gerentes = new ArrayList<Gerente>();

    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<String> keys = new ArrayList<String>();

    private boolean firebaseOffline = false;
    private Handler handler;
    private Thread thread;

    private LinearLayout linearLayout;
    private EditText editText_Nome;
    private EditText editText_Idade;
    private Gerente gerente;

    private Button button_Salvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_lista_gerente);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_DatabaseListaGerenteActivity);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout_Database_Gerente);
        editText_Nome = (EditText)findViewById(R.id.editText_Database_Gerente_Nome);
        editText_Idade = (EditText)findViewById(R.id.editText_Database_Gerente_Idade);
        button_Salvar = (Button)findViewById(R.id.button_Database_Gerente_Salvar);

        database = FirebaseDatabase.getInstance();


        // conexaoFirebaseBD();


        handler = new Handler();
        thread = new Thread(this);
        thread.start();
        button_Salvar.setOnClickListener(this);

        ativarFirebaseOffline();
        iniciarRecyclerView();



    }

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



    @Override
    public void run() {
        try{

            Thread.sleep(10000);

            handler.post(new Runnable() {

                @Override
                public void run() {

                    conexaoFirebaseBD();

                }
            });


        }catch (InterruptedException e){

        }
    }
    private void conexaoFirebaseBD(){



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(".info/connected");


        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                boolean conexao = dataSnapshot.getValue(Boolean.class);


                if(conexao){

                    Toast.makeText(getBaseContext(),"Temos conexao com o BD", Toast.LENGTH_LONG).show();


                }else{


                    if(Util.statusInternet(getBaseContext())){

                        Toast.makeText(getBaseContext(),"BLOQUEIO AO NOSSO BD - ", Toast.LENGTH_LONG).show();

                    }else{


                        // Toast.makeText(getBaseContext(),"SEM CONEXAO COM A INTERNET",Toast.LENGTH_LONG).show();

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    //---------------------------------------INICIAR RECYCLERVIEW------------------------------------------------
    private void iniciarRecyclerView(){




        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView_listaGerente= new RecyclerView_ListaGerente(getBaseContext(),gerentes,this);

        recyclerView.setAdapter(recyclerView_listaGerente);

    }

    //---------------------------------------CLICK NO ITEM------------------------------------------------


    @Override
    public void ClickGerente(Gerente gerente) {
        Intent intent = new Intent(getBaseContext(), DatabaseListaGerenteDadosActivity.class);

        intent.putExtra("gerente",gerente);

         startActivity(intent);

         //Toast.makeText(getBaseContext(),"Nome: "+gerente.getNome()+"\n\nPasta: ",Toast.LENGTH_LONG).show();
    }

    //---------------------------------------Ouvinte ---------------------------------------------------


    private void ouvinte(){




        reference_database = database.getReference().child("BD").child("Gerentes");


        if(childEventListener == null){

            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    String key = dataSnapshot.getKey();

                    keys.add(key);

                    Gerente gerente = dataSnapshot.getValue(Gerente.class);

                    gerente.setId(key);

                    gerentes.add(gerente);

                    recyclerView_listaGerente.notifyDataSetChanged();


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    String key = dataSnapshot.getKey();


                    int index = keys.indexOf(key);

                    Gerente gerente = dataSnapshot.getValue(Gerente.class);

                    gerente.setId(key);


                    gerentes.set(index,gerente);


                    recyclerView_listaGerente.notifyDataSetChanged();


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                    String key = dataSnapshot.getKey();

                    int index = keys.indexOf(key);

                    gerentes.remove(index);

                    keys.remove(index);


                    recyclerView_listaGerente.notifyItemRemoved(index);
                    recyclerView_listaGerente.notifyItemChanged(index,gerentes.size());



                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };



            reference_database.addChildEventListener(childEventListener);
        }


    }





    //--------------------------------------------Ciclos de Vida------------------------------------

    @Override
    protected void onStart() {
        super.onStart();


        ouvinte();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();


        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){



            case R.id.button_Database_Funcionario_Salvar:


                buttonSalvar();

                break;



        }
    }
    private void buttonSalvar(){



        String nome = editText_Nome.getText().toString();

        String idade_String = editText_Idade.getText().toString();


            int idade = Integer.parseInt(idade_String);

            salvarDadosDatabase(nome,idade);

            }



    private void salvarDadosDatabase(String nome, int idade){





        Gerente gerente = new Gerente(nome,idade);


        DatabaseReference databaseReference = database.getReference().child("BD").child("Gerentes");


        databaseReference.setValue(gerente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){

                    Toast.makeText(getBaseContext(),"Sucesso ao realizar Upload - Database",Toast.LENGTH_LONG).show();
                   // progress.dismiss();

                }else{

                    Toast.makeText(getBaseContext(),"Erro ao realizar Upload - Database",Toast.LENGTH_LONG).show();
                  //  progress.dismiss();


                }

            }
        });
    }





}
