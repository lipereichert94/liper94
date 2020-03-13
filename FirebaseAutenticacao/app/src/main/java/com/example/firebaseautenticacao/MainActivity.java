package com.example.firebaseautenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardView_LoginFacebook, cardView_LoginGoogle, cardView_LoginEmail, cardView_loginCadastrar, cardView_LoginAnonimo;
    private Button button_login, button_Cadastrar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;


    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = (Button) findViewById(R.id.button_login);
        button_Cadastrar = (Button) findViewById(R.id.button_cadastrar);
        cardView_LoginGoogle = (CardView)findViewById(R.id.cardView_LoginGoogle);
        cardView_LoginFacebook = (CardView)findViewById(R.id.cardView_LoginFacebook);

        button_login.setOnClickListener(this);
        button_Cadastrar.setOnClickListener(this);
        cardView_LoginGoogle.setOnClickListener(this);
        cardView_LoginFacebook.setOnClickListener(this);
        
        auth = FirebaseAuth.getInstance();
        estadoautenticacao();
        servicosGoogle();

    }








    //-------------------------------------------TRATAMENTO DE CLICKS----------------------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                user = auth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(this,LoginEmailActivity.class));

                }
                else{
                    startActivity(new Intent(this,PrincipalActivity.class));

                }

                break;

            case R.id.button_cadastrar:
                Intent intent = new Intent(this,CadastrarActivity.class);
                startActivity(intent);

                break;
            case R.id.cardView_LoginGoogle:

                signInGoogle();

                break;
            case R.id.cardView_LoginFacebook:

                signInFacebook();

                break;
        }
    }



    //-------------------------------------------------SERVICOS LOGIN--------------------------------------------------

    private void servicosGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, gso);


    }


    //------------------------------------------METODOS DE LOGIN---------------------------------------------------------------

        private void signInFacebook(){

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));

    }

    private void estadoautenticacao() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(getBaseContext(),"Usuário"+user.getEmail()+" está logado",Toast.LENGTH_LONG).show();

                }else{
                }
            }
        };
    }

    private void signInGoogle(){

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account == null){

            Intent intent = googleSignInClient.getSignInIntent();

            startActivityForResult(intent,555);
        }else{

            //já existe alem conectado pelo google
            Toast.makeText(getBaseContext(),"Já logado",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(),PrincipalActivity.class));

            //  googleSignInClient.signOut();


        }

    }

    //---------------------------------------AUTENTICACAO NO FIREBASE---------------------------------------------------------------

    private void adicionarContaGoogleaoFirebase(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getBaseContext(),PrincipalActivity.class));

                        } else {

                            String resultado = task.getException().toString();

                            Util.opcoesErro(getBaseContext(),resultado);

                        }

                        // ...
                    }
                });
    }

    //-------------------------------METODOS DA ACTIVITY--------------------------------------------------------------------------


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 555){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                adicionarContaGoogleaoFirebase(account);


            }catch (ApiException e){

                String resultado = e.getMessage();

                Util.opcoesErro(getBaseContext(),resultado);
            }



        }

    }
}
