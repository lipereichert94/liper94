package com.example.tccsimsim.project.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;
import com.example.tccsimsim.project.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SIMSIM";

    private static final String TABELA_USUARIO = "usuario";
    private static final String ID_USUARIO = "id";
    private static final String NOME_USUARIO = "nome";
    private static final String LOGIN_USUARIO = "login";
    private static final String SENHA_USUARIO = "senha";

    private static final String TABELA_ESTABELECIMENTO = "estabelecimento";
    private static final String ID_ESTABELECIMENTO = "id";
    private static final String NOME_ESTABELECIMENTO = "nome";

    private static final String TABELA_PRODUTO = "produto";
    private static final String ID_PRODUTO = "id";
    private static final String NOME_PRODUTO = "nome";
    private static final String ID_PRODUTO_ESTABELECIMENTO = "id_estabelecimento";

    private static final String TABELA_ATESTADO_SAUDE = "atestado_saude";
    private static final String ID_ATESTADO_SAUDE = "id";
    private static final String DT_REGISTRO_ATESTADO_SAUDE = "dt_registro";
    private static final String DT_VALIDADE_ATESTADO_SAUDE = "dt_validade";
    private static final String ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE = "id_estabelecimento";

    private static final String[] COLUNAS_USUARIO = {ID_USUARIO, NOME_USUARIO, LOGIN_USUARIO, SENHA_USUARIO};
    private static final String[] COLUNAS_ESTABELECIMENTO = {ID_ESTABELECIMENTO,NOME_ESTABELECIMENTO};
    private static final String[] COLUNAS_PRODUTO = {ID_PRODUTO,NOME_PRODUTO,ID_PRODUTO_ESTABELECIMENTO};
    private static final String[] COLUNAS_ATESTADO_SAUDE = {ID_ATESTADO_SAUDE,DT_REGISTRO_ATESTADO_SAUDE,DT_VALIDADE_ATESTADO_SAUDE,ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE};

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

        String CREATE_TABLE2 = "CREATE TABLE "+TABELA_ESTABELECIMENTO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT )";
        db.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "CREATE TABLE "+TABELA_PRODUTO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT,"+
                "id_estabelecimento REFERENCES estabelecimento(id) )";

        db.execSQL(CREATE_TABLE3);
        String CREATE_TABLE4 = "CREATE TABLE "+TABELA_ATESTADO_SAUDE+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dt_registro DATE,"+
                "dt_validade TEXT,"+
                "id_estabelecimento REFERENCES estabelecimento(id) )";

        db.execSQL(CREATE_TABLE4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS estabelecimento");
        db.execSQL("DROP TABLE IF EXISTS produto");
        db.execSQL("DROP TABLE IF EXISTS atestado_saude");

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
        values.put(NOME_USUARIO, user.getNome());
        values.put(LOGIN_USUARIO, user.getLogin());
        values.put(SENHA_USUARIO, user.getSenha());
        db.insert(TABELA_USUARIO, null, values);
        db.close();
    }
    public void addEstabelecimento(Estabelecimento estabelecimento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_ESTABELECIMENTO, estabelecimento.getNome());
        db.insert(TABELA_ESTABELECIMENTO, null, values);
        db.close();
    }
    public void addProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_PRODUTO, produto.getNome());
        values.put(ID_PRODUTO_ESTABELECIMENTO, new Integer(produto.getEstabelecimento().getId()));
        db.insert(TABELA_PRODUTO, null, values);
        db.close();
    }
    public void addAtestadoSaude(Atestado_Saude atestado_saude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_REGISTRO_ATESTADO_SAUDE, atestado_saude.getDt_registro().toString());
        values.put(DT_VALIDADE_ATESTADO_SAUDE, atestado_saude.getDt_validade().toString());
        values.put(ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE, new Integer(atestado_saude.getEstabelecimento().getId()));
        db.insert(TABELA_ATESTADO_SAUDE, null, values);
        db.close();
    }
    public Usuario getUsuario(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USUARIO, // a. tabela
                COLUNAS_USUARIO, // b. colunas
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
    public Estabelecimento getEstabelecimento(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ESTABELECIMENTO, // a. tabela
                COLUNAS_ESTABELECIMENTO, // b. colunas
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
            Estabelecimento estabelecimento = cursorToEstabelecimento(cursor);
            return estabelecimento;
        }
    }
    public Produto getProduto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_PRODUTO, // a. tabela
                COLUNAS_PRODUTO, // b. colunas
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
            Produto produto = cursorToProduto(cursor);
            return produto;
        }
    }
    public Atestado_Saude getAtestadoSaude(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ATESTADO_SAUDE, // a. tabela
                COLUNAS_ATESTADO_SAUDE, // b. colunas
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
            Atestado_Saude atestado_saude = cursorToAtestado_Saude(cursor);
            return atestado_saude;
        }
    }
    public Usuario login(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USUARIO, // a. tabela
                COLUNAS_USUARIO, // b. colunas
                " login = ?", // c. colunas para comparar
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
    private Estabelecimento cursorToEstabelecimento(Cursor cursor) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(Integer.parseInt(cursor.getString(0)));
        estabelecimento.setNome(cursor.getString(1));
        return estabelecimento;
    }
    private Produto cursorToProduto(Cursor cursor) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(2)));
        Produto produto = new Produto();
        produto.setId(Integer.parseInt(cursor.getString(0)));
        produto.setNome(cursor.getString(1));
        produto.setEstabelecimento(estabelecimento);
        return produto;
    }
    private Atestado_Saude cursorToAtestado_Saude(Cursor cursor) {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(3)));

        Atestado_Saude atestado_saude = new Atestado_Saude();
        atestado_saude.setId(Integer.parseInt(cursor.getString(0)));
        atestado_saude.setDt_registro(cursor.getString(1));
        atestado_saude.setDt_validade(cursor.getString(2));
        atestado_saude.setEstabelecimento(estabelecimento);

        return atestado_saude;
    }

    private Date formataStringtoDate(String string) {
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = formatter.parse(string);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
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
    public ArrayList<Estabelecimento> getAllEstabelecimentos() {
        ArrayList<Estabelecimento> listaEstabelecimento = new ArrayList<Estabelecimento>();
        String query = "SELECT * FROM " + TABELA_ESTABELECIMENTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Estabelecimento estabelecimento = cursorToEstabelecimento(cursor);
                listaEstabelecimento.add(estabelecimento);
            } while (cursor.moveToNext());
        }
        return listaEstabelecimento;
    }
    public ArrayList<Produto> getAllProduto() {
        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
        String query = "SELECT * FROM " + TABELA_PRODUTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Produto produto = cursorToProduto(cursor);
                listaProdutos.add(produto);
            } while (cursor.moveToNext());
        }
        return listaProdutos;
    }
    public ArrayList<Atestado_Saude> getAllAtestadoSaude() {
        ArrayList<Atestado_Saude> listaAtestadoSaude = new ArrayList<Atestado_Saude>();
        String query = "SELECT * FROM " + TABELA_ATESTADO_SAUDE +" ORDER by " +DT_VALIDADE_ATESTADO_SAUDE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Atestado_Saude atestado_saude = cursorToAtestado_Saude(cursor);
                listaAtestadoSaude.add(atestado_saude);
            } while (cursor.moveToNext());
        }
        return listaAtestadoSaude;
    }

    public int updateUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_USUARIO, user.getNome());
        values.put(LOGIN_USUARIO, user.getLogin());
        values.put(SENHA_USUARIO, user.getSenha());
        int i = db.update(TABELA_USUARIO, //tabela
                values, // valores
                ID_USUARIO+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int updateEstabelecimento(Estabelecimento estabelecimento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_ESTABELECIMENTO, estabelecimento.getNome());
        int i = db.update(TABELA_ESTABELECIMENTO, //tabela
                values, // valores
                ID_ESTABELECIMENTO+" = ?", // colunas para comparar
                new String[] { String.valueOf(estabelecimento.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int updateProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_PRODUTO, produto.getNome());
        values.put(ID_PRODUTO_ESTABELECIMENTO, new Integer(produto.getEstabelecimento().getId()));
        int i = db.update(TABELA_PRODUTO, //tabela
                values, // valores
                ID_PRODUTO+" = ?", // colunas para comparar
                new String[] { String.valueOf(produto.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int updateAtestadoSaude(Atestado_Saude atestado_saude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_REGISTRO_ATESTADO_SAUDE, atestado_saude.getDt_registro());
        values.put(DT_VALIDADE_ATESTADO_SAUDE, atestado_saude.getDt_validade());
        values.put(ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE, new Integer(atestado_saude.getEstabelecimento().getId()));
        int i = db.update(TABELA_ATESTADO_SAUDE, //tabela
                values, // valores
                ID_ATESTADO_SAUDE+" = ?", // colunas para comparar
                new String[] { String.valueOf(atestado_saude.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_USUARIO, //tabela
                ID_USUARIO+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
    public int deleteEstabelecimento(Estabelecimento estabelecimento) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ESTABELECIMENTO, //tabela
                ID_ESTABELECIMENTO+" = ?", // colunas para comparar
                new String[] { String.valueOf(estabelecimento.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
    public int deleteProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_PRODUTO, //tabela
                ID_PRODUTO+" = ?", // colunas para comparar
                new String[] { String.valueOf(produto.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
    public int deleteAtestadoSaude(Atestado_Saude atestado_saude) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ATESTADO_SAUDE, //tabela
                ID_ATESTADO_SAUDE+" = ?", // colunas para comparar
                new String[] { String.valueOf(atestado_saude.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
}
