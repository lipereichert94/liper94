package com.example.tccsimsim.project.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Usuario;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BDSQLiteHelper bd;
    private TextView txtusuariologado,txtpermissaousuariologado;
    private final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bd = new BDSQLiteHelper(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtpermissaousuariologado = (TextView)headerView.findViewById(R.id.permissaousuariologado);
        txtusuariologado = (TextView)  headerView.findViewById(R.id.usuariologado);
        //Pega a intent de outra activity
        Intent it = getIntent();
        //Recuperei a string da outra activity
        Integer id_usuario = Integer.valueOf(it.getStringExtra("id_usuario"));
        Usuario user = bd.getUsuario(id_usuario);
       Log.d("----->", "id usuario "+id_usuario);

        txtusuariologado.setText(user.getLogin());
        txtpermissaousuariologado.setText(user.getPermissao());

        // Pede permissão para acessar as mídias gravadas no dispositivo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        // Pede permissão para escrever arquivos no dispositivo
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteudo_fragmento,fm.getFragments().get((fm.getFragments().size())-1));
        ft.commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getBaseContext(),login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (id == R.id.nav_produtos) {

            ft.replace(R.id.conteudo_fragmento, new Lista_Produto());
            ft.commit();
        } else if (id == R.id.nav_estabelecimentos) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Estabelecimento());
            ft.commit();
        } else if (id == R.id.nav_usuarios) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Usuario());
            ft.commit();
        } else if (id == R.id.nav_atestadoSaude) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Atestado_Saude());
            ft.commit();
        }
        else if (id == R.id.nav_licencaAmbiental) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Licenca_Ambiental());
            ft.commit();
        }
        else if (id == R.id.nav_mediaMensal) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Media_Mensal());
            ft.commit();
        }
        else if (id == R.id.nav_RNC) {
            ft.replace(R.id.conteudo_fragmento, new Lista_RNC());
            ft.commit();
        }
        else if (id == R.id.nav_AI) {
            ft.replace(R.id.conteudo_fragmento, new Lista_AI());
            ft.commit();
        }
        else if (id == R.id.nav_AnaliseLaboratorial) {
            ft.replace(R.id.conteudo_fragmento, new Lista_Analise_Laboratorial());
            ft.commit();
        }
        else if (id == R.id.nav_Relatorios) {
            ft.replace(R.id.conteudo_fragmento, new Relatorios());
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("----->", "ENTROUU ON RESULT no main actibityyy ");

    }
}
