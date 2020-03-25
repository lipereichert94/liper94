package com.example.tccsimsim.project.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tccsimsim.project.model.Usuario;

import java.util.ArrayList;

public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SIMSIM";
    private static final String TABELA_USUARIO = "usuario";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String LOGIN = "login";
    private static final String SENHA = "senha";


    private static final String[] COLUNAS = {ID, NOME, LOGIN, SENHA};

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABELA_USUARIO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT,"+
                "login TEXT,"+
                "senha TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        this.onCreate(db);
    }
    public void limpatabela(String tabela){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+tabela);
        db.close();
    }

    public void addUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, user.getNome());
        values.put(LOGIN, user.getLogin());
        values.put(SENHA, user.getSenha());
        db.insert(TABELA_USUARIO, null, values);
        db.close();
    }

    public Usuario getUsuario(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USUARIO, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Usuario user = cursorToUsuario(cursor);
            return user;
        }
    }
    public Usuario login(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USUARIO, // a. tabela
                COLUNAS, // b. colunas
                " nome = ?", // c. colunas para comparar
                new String[] { String.valueOf(login) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            if(cursor.getCount() != 0) {
                cursor.moveToFirst();
                Usuario user = cursorToUsuario(cursor);
                return user;
            }else{
                return null;
            }
        }
    }
    private Usuario cursorToUsuario(Cursor cursor) {
        Usuario user = new Usuario();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setNome(cursor.getString(1));
        user.setLogin(cursor.getString(2));
        user.setSenha(cursor.getString(3));
        return user;
    }

    public ArrayList<Usuario> getAllUsuarios() {
        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
        String query = "SELECT * FROM " + TABELA_USUARIO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Usuario user = cursorToUsuario(cursor);
                listaUsuario.add(user);
            } while (cursor.moveToNext());
        }
        return listaUsuario;
    }

    public int updateUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, user.getNome());
        values.put(LOGIN, user.getLogin());
        values.put(SENHA, user.getSenha());
        int i = db.update(TABELA_USUARIO, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_USUARIO, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
}
