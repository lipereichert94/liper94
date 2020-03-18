package com.example.firebasecursods.database;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecursods.R;
import com.example.firebasecursods.Util.DialogAlerta;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DatabaseLerDadosActivity extends AppCompatActivity {

    private TextView textView_Nome;
    private TextView textView_Idade;
    private TextView textView_Fumante;

    private TextView textView_Nome_2;
    private TextView textView_Idade_2;
    private TextView textView_Fumante_2;

    private FirebaseDatabase database;

    private DatabaseReference reference_database;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_ler_dados_activity);

        textView_Nome = (TextView)findViewById(R.id.textView_Database_LerDados_Nome);
        textView_Idade = (TextView)findViewById(R.id.textView_Database_LerDados_Idade);
        textView_Fumante = (TextView)findViewById(R.id.textView_Database_LerDados_Fumante);


        textView_Nome_2 = (TextView)findViewById(R.id.textView_Database_LerDados_Nome_2);
        textView_Idade_2 = (TextView)findViewById(R.id.textView_Database_LerDados_Idade_2);
        textView_Fumante_2 = (TextView)findViewById(R.id.textView_Database_LerDados_Fumante_2);


        database = FirebaseDatabase.getInstance();
        //ouvinte_2();
    }



    //--------------------------------------------1ª Ouvinte------------------------------------


    private void ouvinte_1(){


        DatabaseReference reference = database.getReference().child("BD").child("Gerentes");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                List<Gerente> gerentes = new ArrayList<Gerente>();

                for(DataSnapshot data: dataSnapshot.getChildren()){


                    Gerente gerente = data.getValue(Gerente.class);

                    gerentes.add(gerente);

                }

                textView_Nome.setText(gerentes.get(0).getNome());
                textView_Idade.setText(gerentes.get(0).getIdade()+"");

                textView_Nome_2.setText(gerentes.get(1).getNome());
                textView_Idade_2.setText(gerentes.get(1).getIdade()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    //--------------------------------------------2ª Ouvinte------------------------------------


    private void ouvinte_2(){


        reference_database = database.getReference().child("BD").child("Gerentes");


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                List<Gerente> gerentes = new ArrayList<Gerente>();

                for(DataSnapshot data: dataSnapshot.getChildren()){


                    Gerente gerente = data.getValue(Gerente.class);

                    Log.i("testeOuvinte_2",gerente.getNome()+"");

                    gerentes.add(gerente);

                }

                textView_Nome.setText(gerentes.get(0).getNome());
                textView_Idade.setText(gerentes.get(0).getIdade()+"");

                textView_Nome_2.setText(gerentes.get(1).getNome());
                textView_Idade_2.setText(gerentes.get(1).getIdade()+"");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        reference_database.addValueEventListener(valueEventListener);


    }


    //--------------------------------------------3ª Ouvinte------------------------------------



    private void ouvinte_3(){

        reference_database = database.getReference().child("BD").child("Gerentes");


        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                String chave = dataSnapshot.getKey();

                Gerente gerente = dataSnapshot.getValue(Gerente.class);

                Log.i("testouviente_3_Add",gerente.getNome()+"--"+chave);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                String chave = dataSnapshot.getKey();

                Log.i("testouviente_3_Change","--"+chave);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String chave = dataSnapshot.getKey();

                Log.i("testouviente_3_Remove","--"+chave);

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


    //--------------------------------------------Ciclos de Vida------------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        ouvinte_2();
        ouvinte_3();
    }



    @Override
    protected void onStop() {
        super.onStop();


        if(valueEventListener != null){

            reference_database.removeEventListener(valueEventListener);
        }



        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();


        if(valueEventListener != null){

            reference_database.removeEventListener(valueEventListener);
        }



        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }
}
