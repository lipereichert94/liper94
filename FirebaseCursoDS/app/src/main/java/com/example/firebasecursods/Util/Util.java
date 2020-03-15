package com.example.firebasecursods.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Util {

    public static boolean verificarInternet(Context context){


        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexao.getActiveNetworkInfo();


        if ( informacao != null && informacao.isConnected()){

            return true;

        }else{

            return false;
        }


    }

    public static boolean verificarCampos(Context context, String texto_1, String texto_2){


        if( !texto_1.isEmpty() && !texto_2.isEmpty()){

            if( verificarInternet(context)){

                return true;

            }else{

                Toast.makeText(context,"Sem conexão com a Internet",Toast.LENGTH_LONG).show();

                return false;
            }

        }else{

            Toast.makeText(context,"Preencha os campos", Toast.LENGTH_LONG).show();
            return false;
        }




    }
}
