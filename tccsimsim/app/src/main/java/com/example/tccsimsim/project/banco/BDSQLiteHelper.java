package com.example.tccsimsim.project.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Analise_Laboratorial;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;
import com.example.tccsimsim.project.model.Produto;
import com.example.tccsimsim.project.model.RNC;
import com.example.tccsimsim.project.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final String PERMISSAO_USUARIO = "permissao";

    private static final String TABELA_ESTABELECIMENTO = "estabelecimento";
    private static final String ID_ESTABELECIMENTO = "id";
    private static final String NOME_ESTABELECIMENTO = "nome";
    private static final String NOME__FANTASIA_ESTABELECIMENTO = "nome_fantasia";
    private static final String CLASSIFICACAO = "classificacao";
    private static final String CNPJ = "cnpj";
    private static final String INSCRICAO_ESTADUAL = "inscricao_estadual";
    private static final String INSCRICAO_MUNICIPAL = "inscricao_municipal";
    private static final String ENDERECO = "endereco";
    private static final String ENDERECO_ELETRONICO = "endereco_eletronico";
    private static final String DATA_REGISTRO = "dt_registro";
    private static final String FONE = "fone";

    private static final String TABELA_PRODUTO = "produto";
    private static final String ID_PRODUTO = "id";
    private static final String NOME_PRODUTO = "nome";
    private static final String ID_PRODUTO_ESTABELECIMENTO = "id_estabelecimento";

    private static final String TABELA_ATESTADO_SAUDE = "atestado_saude";
    private static final String ID_ATESTADO_SAUDE = "id";
    private static final String DT_REGISTRO_ATESTADO_SAUDE = "dt_registro";
    private static final String DT_VALIDADE_ATESTADO_SAUDE = "dt_validade";
    private static final String ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE = "id_estabelecimento";

    private static final String TABELA_LICENCA_AMBIENTAL = "licenca_ambiental";
    private static final String ID_LICENCA_AMBIENTAL = "id";
    private static final String DT_REGISTRO_LICENCA_AMBIENTAL = "dt_registro";
    private static final String DT_VALIDADE_LICENCA_AMBIENTAL = "dt_validade";
    private static final String ID_PRODUTO_ESTABELECIMENTO_LICENCA_AMBIENTAL = "id_estabelecimento";

    private static final String TABELA_MEDIA_MENSAL = "media_mensal";
    private static final String ID_MEDIA_MENSAL = "id";
    private static final String DT_MEDIA_MENSAL = "data";
    private static final String QUANTIDADE_MEDIA_MENSAL = "quantidade";
    private static final String ID_PRODUTO_MEDIA_MENSAL = "id_Produto";

    private static final String TABELA_RNC = "rnc";
    private static final String ID_RNC = "id";
    private static final String DT_INSPECAO = "dt_inspecao";
    private static final String DESCRICAO = "descricao";
    private static final String DT_VERIFICACAO = "dt_verificacao";
    private static final String SITUACAO = "situacao";
    private static final String URL_IMAGEM = "url_imagem";
    private static final String ID_ESTABELECIMENTO_RNC= "id_estabelecimento";

    private static final String TABELA_AI = "ai";
    private static final String ID_AI = "id";
    private static final String DT_AI = "dt_ai";
    private static final String INFRACAO_AI = "infracao_ai";
    private static final String PENALIDADE_AI = "penalidade_ai";
    private static final String SITUACAO_AI= "situacao_ai";
    private static final String ID_ESTABELECIMENTO_AI= "id_estabelecimento";

    private static final String TABELA_ANALISE_LABORATORIAL = "analise_laboratorial";
    private static final String ID_ANALISE_LABORATORIAL = "id";
    private static final String TIPO_ANALISE_LABORATORIAL = "tipo";
    private static final String DT_COLETA = "dt_coleta";
    private static final String SITUACAO_COLETA = "situacao_coleta";
    private static final String NOTIFICACAO = "notificacao";
    private static final String DT_NOVA_COLETA = "dt_nova_coleta";
    private static final String SITUACAO_NOVA_COLETA = "situacao_nova_coleta";
    private static final String ID_PRODUTO_ANALISE_LABORATORIAL = "id_produto";

    private static final String[] COLUNAS_USUARIO = {ID_USUARIO, NOME_USUARIO, LOGIN_USUARIO, SENHA_USUARIO,PERMISSAO_USUARIO};
    private static final String[] COLUNAS_ESTABELECIMENTO = {ID_ESTABELECIMENTO,NOME_ESTABELECIMENTO,NOME__FANTASIA_ESTABELECIMENTO,CLASSIFICACAO,CNPJ,INSCRICAO_ESTADUAL,INSCRICAO_MUNICIPAL,ENDERECO,ENDERECO_ELETRONICO,DATA_REGISTRO,FONE};
    private static final String[] COLUNAS_PRODUTO = {ID_PRODUTO,NOME_PRODUTO,ID_PRODUTO_ESTABELECIMENTO};
    private static final String[] COLUNAS_ATESTADO_SAUDE = {ID_ATESTADO_SAUDE,DT_REGISTRO_ATESTADO_SAUDE,DT_VALIDADE_ATESTADO_SAUDE,ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE};
    private static final String[] COLUNAS_LICENCA_AMBIENTAL = {ID_LICENCA_AMBIENTAL,DT_REGISTRO_LICENCA_AMBIENTAL,DT_VALIDADE_LICENCA_AMBIENTAL,ID_PRODUTO_ESTABELECIMENTO_LICENCA_AMBIENTAL};
    private static final String[] COLUNAS_MEDIA_MENSAL = {ID_MEDIA_MENSAL,DT_MEDIA_MENSAL,QUANTIDADE_MEDIA_MENSAL,ID_PRODUTO_MEDIA_MENSAL};
    private static final String[] COLUNAS_RNC = {ID_RNC,DT_INSPECAO,DESCRICAO,DT_VERIFICACAO,SITUACAO,URL_IMAGEM,ID_ESTABELECIMENTO_RNC};
    private static final String[] COLUNAS_AI = {ID_AI,DT_AI,INFRACAO_AI,PENALIDADE_AI,SITUACAO_AI,ID_ESTABELECIMENTO_AI};
    private static final String[] COLUNAS_ANALISE_LABORATORIAL = {ID_ANALISE_LABORATORIAL,TIPO_ANALISE_LABORATORIAL,DT_COLETA,SITUACAO_COLETA,NOTIFICACAO,DT_NOVA_COLETA,SITUACAO_NOVA_COLETA,ID_PRODUTO_ANALISE_LABORATORIAL};


    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");

        String CREATE_TABLE = "CREATE TABLE "+TABELA_USUARIO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT,"+
                "login TEXT,"+
                "senha TEXT,"+
                "permissao TEXT )";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 = "CREATE TABLE "+TABELA_ESTABELECIMENTO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT,"+
                "nome_fantasia TEXT,"+
                "classificacao TEXT,"+
                "cnpj INTEGER,"+
                "inscricao_estadual INTEGER,"+
                "inscricao_municipal INTEGER,"+
                "endereco TEXT,"+
                "endereco_eletronico TEXT,"+
                "dt_registro DATE,"+
                "fone INTEGER )";
        db.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "CREATE TABLE "+TABELA_PRODUTO+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT,"+
                "id_estabelecimento REFERENCES estabelecimento(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE3);
        String CREATE_TABLE4 = "CREATE TABLE "+TABELA_ATESTADO_SAUDE+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dt_registro DATE,"+
                "dt_validade DATE,"+
                "id_estabelecimento REFERENCES estabelecimento(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE4);
        String CREATE_TABLE5 = "CREATE TABLE "+TABELA_LICENCA_AMBIENTAL+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dt_registro DATE,"+
                "dt_validade DATE,"+
                "id_estabelecimento REFERENCES estabelecimento(id)" +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE5);
        String CREATE_TABLE6 = "CREATE TABLE "+TABELA_MEDIA_MENSAL+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "data DATE,"+
                "quantidade INTEGER,"+
                "id_produto REFERENCES produto(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE6);
        String CREATE_TABLE7 = "CREATE TABLE "+TABELA_RNC+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dt_inspecao DATE,"+
                "descricao TEXT,"+
                "dt_verificacao DATE,"+
                "situacao TEXT,"+
                "url_imagem TEXT,"+
                "id_estabelecimento REFERENCES estabelecimento(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE7);
        String CREATE_TABLE8 = "CREATE TABLE "+TABELA_AI+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dt_ai DATE,"+
                "infracao_ai TEXT,"+
                "penalidade_ai DATE,"+
                "situacao_ai TEXT,"+
                "id_estabelecimento REFERENCES estabelecimento(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE8);
        String CREATE_TABLE9 = "CREATE TABLE "+TABELA_ANALISE_LABORATORIAL+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "tipo TEXT,"+
                "dt_coleta DATE,"+
                "situacao_coleta TEXT,"+
                "notificacao TEXT,"+
                "dt_nova_coleta DATE,"+
                "situacao_nova_coleta TEXT,"+
                "id_produto REFERENCES produto(id) " +
                "ON UPDATE RESTRICT\n" +
                "ON DELETE RESTRICT)";

        db.execSQL(CREATE_TABLE9);
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON");

        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS estabelecimento");
        db.execSQL("DROP TABLE IF EXISTS produto");
        db.execSQL("DROP TABLE IF EXISTS atestado_saude");
        db.execSQL("DROP TABLE IF EXISTS licenca_ambiental");
        db.execSQL("DROP TABLE IF EXISTS media_mensal");
        db.execSQL("DROP TABLE IF EXISTS rnc");
        db.execSQL("DROP TABLE IF EXISTS ai");
        db.execSQL("DROP TABLE IF EXISTS analise_laboratorial");

        this.onCreate(db);
    }
    public void limpatabela(String tabela){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+tabela);
        db.close();
    }

    //---------------------------------------------USUARIO---------------------------------------------------
    public void addUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_USUARIO, user.getNome());
        values.put(LOGIN_USUARIO, user.getLogin());
        values.put(SENHA_USUARIO, user.getSenha());
        values.put(PERMISSAO_USUARIO, user.getPermissao());
        db.insert(TABELA_USUARIO, null, values);
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
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Usuario user = cursorToUsuario(cursor);
            db.close();
            return user;
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
            db.close();
            return null;
        } else {
            if(cursor.getCount() != 0) {
                cursor.moveToFirst();
                Usuario user = cursorToUsuario(cursor);
                db.close();
                return user;
            }else{
                db.close();
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
        user.setPermissao(cursor.getString(4));
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
        db.close();
        return listaUsuario;
    }
    public int updateUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_USUARIO, user.getNome());
        values.put(LOGIN_USUARIO, user.getLogin());
        values.put(SENHA_USUARIO, user.getSenha());
        values.put(PERMISSAO_USUARIO, user.getPermissao());
        int i = db.update(TABELA_USUARIO, //tabela
                values, // valores
                ID_USUARIO+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) }); //parâmetros
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
    //---------------------------------------------ESTABELECIMENTO--------------------------------------------------
    public void addEstabelecimento(Estabelecimento estabelecimento) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_ESTABELECIMENTO, estabelecimento.getNome());
        values.put(NOME__FANTASIA_ESTABELECIMENTO,estabelecimento.getNome_fantasia());
        values.put(CLASSIFICACAO,estabelecimento.getClassificacao());
        values.put(CNPJ,estabelecimento.getCnpj());
        values.put(INSCRICAO_ESTADUAL,estabelecimento.getInscricao_estadual());
        values.put(INSCRICAO_MUNICIPAL,estabelecimento.getInscricao_municipal());
        values.put(ENDERECO,estabelecimento.getEndereco());
        values.put(ENDERECO_ELETRONICO,estabelecimento.getEndereco_eletronico());
        values.put(DATA_REGISTRO,formataDataddmmaaaatoyyyymmdd(estabelecimento.getDt_registro()));
        values.put(FONE,estabelecimento.getFone());
        db.insert(TABELA_ESTABELECIMENTO, null, values);
        db.close();
    }
    public Estabelecimento getEstabelecimento(int id) throws ParseException {
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
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Estabelecimento estabelecimento = cursorToEstabelecimento(cursor);
            db.close();
            return estabelecimento;
        }
    }

    private Estabelecimento cursorToEstabelecimento(Cursor cursor) throws ParseException {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(Integer.parseInt(cursor.getString(0)));
        estabelecimento.setNome(cursor.getString(1));
        estabelecimento.setNome_fantasia(cursor.getString(2));
        estabelecimento.setClassificacao(cursor.getString(3));
        estabelecimento.setCnpj(Integer.parseInt(cursor.getString(4)));
        estabelecimento.setInscricao_estadual(Integer.parseInt(cursor.getString(5)));
        estabelecimento.setInscricao_municipal(Integer.parseInt(cursor.getString(6)));
        estabelecimento.setEndereco(cursor.getString(7));
        estabelecimento.setEndereco_eletronico(cursor.getString(8));
        estabelecimento.setDt_registro(formataDatayyyymmddtoddmmaaa(cursor.getString(9)));
        estabelecimento.setFone(cursor.getString(10));
        return estabelecimento;
    }
    public ArrayList<Estabelecimento> getAllEstabelecimentos() throws ParseException {
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
        db.close();
        return listaEstabelecimento;
    }
    public int updateEstabelecimento(Estabelecimento estabelecimento) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_ESTABELECIMENTO, estabelecimento.getNome());
        values.put(NOME__FANTASIA_ESTABELECIMENTO,estabelecimento.getNome_fantasia());
        values.put(CLASSIFICACAO,estabelecimento.getClassificacao());
        values.put(CNPJ,estabelecimento.getCnpj());
        values.put(INSCRICAO_ESTADUAL,estabelecimento.getInscricao_estadual());
        values.put(INSCRICAO_MUNICIPAL,estabelecimento.getInscricao_municipal());
        values.put(ENDERECO,estabelecimento.getEndereco());
        values.put(ENDERECO_ELETRONICO,estabelecimento.getEndereco_eletronico());
        values.put(DATA_REGISTRO,formataDataddmmaaaatoyyyymmdd(estabelecimento.getDt_registro()));
        values.put(FONE,estabelecimento.getFone());
        int i = db.update(TABELA_ESTABELECIMENTO, //tabela
                values, // valores
                ID_ESTABELECIMENTO+" = ?", // colunas para comparar
                new String[] { String.valueOf(estabelecimento.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteEstabelecimento(Estabelecimento estabelecimento) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;
        try {
             i = db.delete(TABELA_ESTABELECIMENTO, //tabela
                    ID_ESTABELECIMENTO + " = ?", // colunas para comparar
                    new String[]{String.valueOf(estabelecimento.getId())}); //parâmetro
            db.close();
            Log.d("----->", "método retornou i = "+i);
            return i; // número de linhas excluídas

        }catch (Exception e){
            Log.d("----->", "método retornou i = "+i);
            Log.d("----->", "Exception = "+e);
            db.close();
            return i;
        }
    }
    //---------------------------------------------PRODUTO------------------------------------------------------------
    public void addProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME_PRODUTO, produto.getNome());
        values.put(ID_PRODUTO_ESTABELECIMENTO, new Integer(produto.getEstabelecimento().getId()));
        db.insert(TABELA_PRODUTO, null, values);
        db.close();
    }

    public Produto getProduto(int id) throws ParseException {
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
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Produto produto = cursorToProduto(cursor);
            db.close();
            return produto;
        }
    }
    private Produto cursorToProduto(Cursor cursor) throws ParseException {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(2)));
        Produto produto = new Produto();
        produto.setId(Integer.parseInt(cursor.getString(0)));
        produto.setNome(cursor.getString(1));
        produto.setEstabelecimento(estabelecimento);
        return produto;
    }
    public ArrayList<Produto> getAllProduto() throws ParseException {
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
        db.close();
        return listaProdutos;
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
    public int deleteProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;
        try {
            i = db.delete(TABELA_PRODUTO, //tabela
                    ID_PRODUTO + " = ?", // colunas para comparar
                    new String[]{String.valueOf(produto.getId())}); //parâmetro
            db.close();
            Log.d("----->", "método retornou i = "+i);
            return i; // número de linhas excluídas

        }catch (Exception e){
            Log.d("----->", "método retornou i = "+i);
            Log.d("----->", "Exception = "+e);
            db.close();

            return i;
        }
    }

    //-----------------------------------------ATESTADO DE SAÚDE-------------------------------------------------------
    public void addAtestadoSaude(Atestado_Saude atestado_saude) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_REGISTRO_ATESTADO_SAUDE, formataDataddmmaaaatoyyyymmdd(atestado_saude.getDt_registro().toString()));
        values.put(DT_VALIDADE_ATESTADO_SAUDE, formataDataddmmaaaatoyyyymmdd(atestado_saude.getDt_validade()));
        values.put(ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE, new Integer(atestado_saude.getEstabelecimento().getId()));
        db.insert(TABELA_ATESTADO_SAUDE, null, values);
        db.close();
    }

    public Atestado_Saude getAtestadoSaude(int id) throws ParseException {
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
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Atestado_Saude atestado_saude = cursorToAtestado_Saude(cursor);
            db.close();
            return atestado_saude;
        }
    }

    private Atestado_Saude cursorToAtestado_Saude(Cursor cursor) throws ParseException {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(3)));

        Atestado_Saude atestado_saude = new Atestado_Saude();
        atestado_saude.setId(Integer.parseInt(cursor.getString(0)));
        atestado_saude.setDt_registro(formataDatayyyymmddtoddmmaaa(cursor.getString(1)));
        atestado_saude.setDt_validade(formataDatayyyymmddtoddmmaaa(cursor.getString(2)));
        atestado_saude.setEstabelecimento(estabelecimento);

        return atestado_saude;
    }
    public ArrayList<Atestado_Saude> getAllAtestadoSaude() throws ParseException {
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
        db.close();
        return listaAtestadoSaude;
    }
    public int updateAtestadoSaude(Atestado_Saude atestado_saude) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_REGISTRO_ATESTADO_SAUDE, formataDataddmmaaaatoyyyymmdd(atestado_saude.getDt_registro()));
        values.put(DT_VALIDADE_ATESTADO_SAUDE, formataDataddmmaaaatoyyyymmdd(atestado_saude.getDt_validade()));
        values.put(ID_PRODUTO_ESTABELECIMENTO_ATESTADO_SAUDE, new Integer(atestado_saude.getEstabelecimento().getId()));
        int i = db.update(TABELA_ATESTADO_SAUDE, //tabela
                values, // valores
                ID_ATESTADO_SAUDE+" = ?", // colunas para comparar
                new String[] { String.valueOf(atestado_saude.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteAtestadoSaude(Atestado_Saude atestado_saude) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ATESTADO_SAUDE, //tabela
                ID_ATESTADO_SAUDE+" = ?", // colunas para comparar
                new String[] { String.valueOf(atestado_saude.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }

    //-----------------------------------------LICENCA AMBIENTAL-------------------------------------------------------
    public void addLicencaAmbiental(Licenca_Ambiental licenca_ambiental) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("----->", "ENTROU ADD LICENC");

        values.put(DT_REGISTRO_LICENCA_AMBIENTAL, formataDataddmmaaaatoyyyymmdd(licenca_ambiental.getDt_registro()));
        values.put(DT_VALIDADE_LICENCA_AMBIENTAL, formataDataddmmaaaatoyyyymmdd(licenca_ambiental.getDt_validade()));
        values.put(ID_PRODUTO_ESTABELECIMENTO_LICENCA_AMBIENTAL, new Integer(licenca_ambiental.getEstabelecimento().getId()));
        db.insert(TABELA_LICENCA_AMBIENTAL, null, values);
        db.close();
    }

    public Licenca_Ambiental getLicencaAmbiental(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_LICENCA_AMBIENTAL, // a. tabela
                COLUNAS_LICENCA_AMBIENTAL, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Licenca_Ambiental licenca_ambiental = cursorToLicencaAmbiental(cursor);
            db.close();
            return licenca_ambiental;
        }
    }

    private Licenca_Ambiental cursorToLicencaAmbiental(Cursor cursor) throws ParseException {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(3)));

        Licenca_Ambiental licenca_ambiental = new Licenca_Ambiental();
        licenca_ambiental.setId(Integer.parseInt(cursor.getString(0)));
        licenca_ambiental.setDt_registro(formataDatayyyymmddtoddmmaaa(cursor.getString(1)));
        licenca_ambiental.setDt_validade(formataDatayyyymmddtoddmmaaa(cursor.getString(2)));
        licenca_ambiental.setEstabelecimento(estabelecimento);

        return licenca_ambiental;
    }
    public ArrayList<Licenca_Ambiental> getAllLicencaAmbiental() throws ParseException {
        ArrayList<Licenca_Ambiental> listaLicencaAmbiental = new ArrayList<Licenca_Ambiental>();
        String query = "SELECT * FROM " + TABELA_LICENCA_AMBIENTAL +" ORDER by " +DT_VALIDADE_LICENCA_AMBIENTAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Licenca_Ambiental licenca_ambiental = cursorToLicencaAmbiental(cursor);
                listaLicencaAmbiental.add(licenca_ambiental);
            } while (cursor.moveToNext());
        }
        db.close();
        return listaLicencaAmbiental;
    }
    public int updateLicencaAmbiental(Licenca_Ambiental licenca_ambiental) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_REGISTRO_LICENCA_AMBIENTAL, formataDataddmmaaaatoyyyymmdd(licenca_ambiental.getDt_registro()));
        values.put(DT_VALIDADE_LICENCA_AMBIENTAL, formataDataddmmaaaatoyyyymmdd(licenca_ambiental.getDt_validade()));
        values.put(ID_PRODUTO_ESTABELECIMENTO_LICENCA_AMBIENTAL, new Integer(licenca_ambiental.getEstabelecimento().getId()));
        int i = db.update(TABELA_LICENCA_AMBIENTAL, //tabela
                values, // valores
                ID_LICENCA_AMBIENTAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(licenca_ambiental.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteLicencaAmbiental(Licenca_Ambiental licenca_ambiental) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_LICENCA_AMBIENTAL, //tabela
                ID_LICENCA_AMBIENTAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(licenca_ambiental.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }


    //-----------------------------------------MEDIA MENSAL -------------------------------------------------------
    public void addMediaMensal(Media_Mensal media_mensal) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_MEDIA_MENSAL, formataDataddmmaaaatoyyyymmdd(media_mensal.getDt_media_mensal()));
        values.put(QUANTIDADE_MEDIA_MENSAL,media_mensal.getQuantidade());
        values.put(ID_PRODUTO_MEDIA_MENSAL, new Integer(media_mensal.getProduto().getId()));
        db.insert(TABELA_MEDIA_MENSAL, null, values);
        db.close();
    }

    public Media_Mensal getMediaMensal(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_MEDIA_MENSAL, // a. tabela
                COLUNAS_MEDIA_MENSAL, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Media_Mensal media_mensal = cursorToMediaMensal(cursor);
            db.close();
            return media_mensal;
        }
    }

    private Media_Mensal cursorToMediaMensal(Cursor cursor) throws ParseException {

        Produto produto = new Produto();
        produto =  getProduto(Integer.parseInt(cursor.getString(3)));

        Media_Mensal media_mensal = new Media_Mensal();
        media_mensal.setId(Integer.parseInt(cursor.getString(0)));
        media_mensal.setDt_media_mensal(formataDatayyyymmddtoddmmaaa(cursor.getString(1)));
        media_mensal.setQuantidade(new Integer(cursor.getString(2)));
        media_mensal.setProduto(produto);

        return media_mensal;
    }
    public ArrayList<Media_Mensal> getAllMediaMensal() throws ParseException {
        ArrayList<Media_Mensal> listaMediaMensal = new ArrayList<Media_Mensal>();
        String query = "SELECT * FROM " + TABELA_MEDIA_MENSAL +" ORDER by " +ID_PRODUTO_MEDIA_MENSAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Media_Mensal media_mensal = cursorToMediaMensal(cursor);
                listaMediaMensal.add(media_mensal);
            } while (cursor.moveToNext());
        }
        db.close();
        return listaMediaMensal;
    }
    public int updateMediaMensal(Media_Mensal media_mensal) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_MEDIA_MENSAL, formataDataddmmaaaatoyyyymmdd(media_mensal.getDt_media_mensal()));
        values.put(QUANTIDADE_MEDIA_MENSAL,media_mensal.getQuantidade());
        values.put(ID_PRODUTO_MEDIA_MENSAL, new Integer(media_mensal.getProduto().getId()));
        int i = db.update(TABELA_MEDIA_MENSAL, //tabela
                values, // valores
                ID_MEDIA_MENSAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(media_mensal.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteMediaMensal(Media_Mensal media_mensal) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_MEDIA_MENSAL, //tabela
                ID_MEDIA_MENSAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(media_mensal.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }


    //-----------------------------------------RELATORIO DE NAO CONFORMIDADE - RNC-------------------------------------------------------
    public void addRNC(RNC rnc) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_INSPECAO, formataDataddmmaaaatoyyyymmdd(rnc.getDt_inspecao()));
        values.put(DESCRICAO,rnc.getDescricao());
        values.put(DT_VERIFICACAO, formataDataddmmaaaatoyyyymmdd(rnc.getDt_verificacao()));
        values.put(SITUACAO,rnc.getSituacao());
        values.put(URL_IMAGEM,rnc.getUrl_imagem());
        values.put(ID_ESTABELECIMENTO_RNC, new Integer(rnc.getEstabelecimento().getId()));
        db.insert(TABELA_RNC, null, values);
        db.close();
    }

    public RNC getRNC(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_RNC, // a. tabela
                COLUNAS_RNC, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            RNC rnc = cursorToRNC(cursor);
            db.close();
            return rnc;
        }
    }

    private RNC cursorToRNC(Cursor cursor) throws ParseException {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(6)));

        RNC rnc = new RNC();
        rnc.setId(Integer.parseInt(cursor.getString(0)));
        rnc.setDt_inspecao(formataDatayyyymmddtoddmmaaa(cursor.getString(1)));
        rnc.setDescricao(cursor.getString(2));
        rnc.setDt_verificacao(formataDatayyyymmddtoddmmaaa(cursor.getString(3)));
        rnc.setSituacao(cursor.getString(4));
        rnc.setUrl_imagem(cursor.getString(5));
        rnc.setEstabelecimento(estabelecimento);

        return rnc;
    }
    public ArrayList<RNC> getAllRNC() throws ParseException {
        ArrayList<RNC> listaRNC = new ArrayList<RNC>();
        String query = "SELECT * FROM " + TABELA_RNC +" ORDER by " +DT_VERIFICACAO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                RNC rnc = cursorToRNC(cursor);
                listaRNC.add(rnc);
            } while (cursor.moveToNext());
        }
        db.close();
        return listaRNC;
    }
    public int updateRNC(RNC rnc) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_INSPECAO, formataDataddmmaaaatoyyyymmdd(rnc.getDt_inspecao()));
        values.put(DESCRICAO, rnc.getDescricao());
        values.put(DT_VERIFICACAO,formataDataddmmaaaatoyyyymmdd(rnc.getDt_verificacao()));
        values.put(SITUACAO,rnc.getSituacao());
        values.put(URL_IMAGEM,rnc.getUrl_imagem());
        values.put(ID_ESTABELECIMENTO_RNC, new Integer(rnc.getEstabelecimento().getId()));
        int i = db.update(TABELA_RNC, //tabela
                values, // valores
                ID_RNC+" = ?", // colunas para comparar
                new String[] { String.valueOf(rnc.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteRNC(RNC rnc) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_RNC, //tabela
                ID_RNC+" = ?", // colunas para comparar
                new String[] { String.valueOf(rnc.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }


    //-----------------------------------------AUTO DE INFRAÇÃO-----------------------------------
    public void addAI(AI ai) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_AI, formataDataddmmaaaatoyyyymmdd(ai.getDt_ai()));
        values.put(INFRACAO_AI,ai.getInfracao_ai());
        values.put(PENALIDADE_AI,ai.getPenalidade_ai());
        values.put(SITUACAO_AI,ai.getSituacao_ai());
        values.put(ID_ESTABELECIMENTO_AI, new Integer(ai.getEstabelecimento().getId()));
        db.insert(TABELA_AI, null, values);
        db.close();
    }

    public AI getAI(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_AI, // a. tabela
                COLUNAS_AI, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            AI ai = cursorToAI(cursor);
            db.close();
            return ai;
        }
    }

    private AI cursorToAI(Cursor cursor) throws ParseException {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento =  getEstabelecimento(Integer.parseInt(cursor.getString(5)));

        AI ai = new AI();
        ai.setId(Integer.parseInt(cursor.getString(0)));
        ai.setDt_ai(formataDatayyyymmddtoddmmaaa(cursor.getString(1)));
        ai.setInfracao_ai(cursor.getString(2));
        ai.setPenalidade_ai(cursor.getString(3));
        ai.setSituacao_ai(cursor.getString(4));
        ai.setEstabelecimento(estabelecimento);

        return ai;
    }
    public ArrayList<AI> getAllAI() throws ParseException {
        ArrayList<AI> listaAI = new ArrayList<AI>();
        String query = "SELECT * FROM " + TABELA_AI +" ORDER by " +ID_ESTABELECIMENTO_AI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                AI ai = cursorToAI(cursor);
                listaAI.add(ai);
            } while (cursor.moveToNext());
        }
        db.close();
        return listaAI;
    }
    public int updateAI(AI ai) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DT_AI, formataDataddmmaaaatoyyyymmdd(ai.getDt_ai()));
        values.put(INFRACAO_AI, ai.getInfracao_ai());
        values.put(PENALIDADE_AI,ai.getPenalidade_ai());
        values.put(SITUACAO_AI,ai.getSituacao_ai());
        values.put(ID_ESTABELECIMENTO_AI, new Integer(ai.getEstabelecimento().getId()));
        int i = db.update(TABELA_AI, //tabela
                values, // valores
                ID_AI+" = ?", // colunas para comparar
                new String[] { String.valueOf(ai.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteAI(AI ai) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_AI, //tabela
                ID_AI+" = ?", // colunas para comparar
                new String[] { String.valueOf(ai.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }

    //-----------------------------------------ANALISE LABORATORIAL -----------------------------------
    public void addAnaliseLaboratorial(Analise_Laboratorial analise_laboratorial) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIPO_ANALISE_LABORATORIAL,analise_laboratorial.getTipo());
        values.put(DT_COLETA, formataDataddmmaaaatoyyyymmdd(analise_laboratorial.getDt_coleta()));
        values.put(SITUACAO_COLETA,analise_laboratorial.getSituacao_coleta());
        values.put(NOTIFICACAO,analise_laboratorial.getNotificacao());
        values.put(DT_NOVA_COLETA,formataDataddmmaaaatoyyyymmdd(analise_laboratorial.getDt_nova_coleta()));
        values.put(SITUACAO_NOVA_COLETA,analise_laboratorial.getSituacao_nova_coleta());
        values.put(ID_PRODUTO_ANALISE_LABORATORIAL, new Integer(analise_laboratorial.getProduto().getId()));
        db.insert(TABELA_ANALISE_LABORATORIAL, null, values);
        db.close();
    }

    public Analise_Laboratorial getAnaliseLaboratorial(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ANALISE_LABORATORIAL, // a. tabela
                COLUNAS_ANALISE_LABORATORIAL, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            Analise_Laboratorial analise_laboratorial = cursorToAnaliseLaboratorial(cursor);
            db.close();
            return analise_laboratorial;
        }
    }

    private Analise_Laboratorial cursorToAnaliseLaboratorial(Cursor cursor) throws ParseException {

        Produto produto = new Produto();
        produto =  getProduto(Integer.parseInt(cursor.getString(7)));

        Analise_Laboratorial analise_laboratorial = new Analise_Laboratorial();
        analise_laboratorial.setId(Integer.parseInt(cursor.getString(0)));
        analise_laboratorial.setTipo(cursor.getString(1));
        analise_laboratorial.setDt_coleta(formataDatayyyymmddtoddmmaaa(cursor.getString(2)));
        analise_laboratorial.setSituacao_coleta(cursor.getString(3));
        analise_laboratorial.setNotificacao(cursor.getString(4));
        analise_laboratorial.setDt_nova_coleta(formataDatayyyymmddtoddmmaaa(cursor.getString(5)));
        analise_laboratorial.setSituacao_nova_coleta(cursor.getString(6));
        analise_laboratorial.setProduto(produto);

        return analise_laboratorial;
    }
    public ArrayList<Analise_Laboratorial> getAllAnaliseLaboratorial() throws ParseException {
        ArrayList<Analise_Laboratorial> listaAnaliseLaboratorial = new ArrayList<Analise_Laboratorial>();
        String query = "SELECT * FROM " + TABELA_ANALISE_LABORATORIAL +" ORDER by " +ID_PRODUTO_ANALISE_LABORATORIAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Analise_Laboratorial analise_laboratorial = cursorToAnaliseLaboratorial(cursor);
                listaAnaliseLaboratorial.add(analise_laboratorial);
            } while (cursor.moveToNext());
        }
        db.close();
        return listaAnaliseLaboratorial;
    }
    public int updateAnaliseLaboratorial(Analise_Laboratorial analise_laboratorial) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIPO_ANALISE_LABORATORIAL,analise_laboratorial.getTipo());
        values.put(DT_COLETA, formataDataddmmaaaatoyyyymmdd(analise_laboratorial.getDt_coleta()));
        values.put(SITUACAO_COLETA, analise_laboratorial.getSituacao_coleta());
        values.put(NOTIFICACAO,analise_laboratorial.getNotificacao());
        values.put(DT_NOVA_COLETA,formataDataddmmaaaatoyyyymmdd(analise_laboratorial.getDt_nova_coleta()));
        values.put(SITUACAO_NOVA_COLETA,analise_laboratorial.getSituacao_nova_coleta());
        values.put(ID_PRODUTO_ANALISE_LABORATORIAL, new Integer(analise_laboratorial.getProduto().getId()));
        int i = db.update(TABELA_ANALISE_LABORATORIAL, //tabela
                values, // valores
                ID_ANALISE_LABORATORIAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(analise_laboratorial.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
    public int deleteAnaliseLaboratorial(Analise_Laboratorial analise_laboratorial) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ANALISE_LABORATORIAL, //tabela
                ID_ANALISE_LABORATORIAL+" = ?", // colunas para comparar
                new String[] { String.valueOf(analise_laboratorial.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
    //--------------------------------------------------- OUTROS METODOS ---------------------------------------------------------
    private String formataDataddmmaaaatoyyyymmdd(String data) throws ParseException {
        if (data != null) {

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        date = dt.parse(data);

        // *** same for the format String below

        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("----->", "DATA RECEBIDA "+data);
        data = dt1.format(date);

        Log.d("----->", "DATA FORMATADA "+data);

        return data;
        }else{
            return data;
        }
    }

    private String formataDatayyyymmddtoddmmaaa(String data) throws ParseException {
        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        if (data != null) {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            date = dt.parse(data);

            // *** same for the format String below

            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
            Log.d("----->", "DATA RECEBIDA " + data);
            data = dt1.format(date);

            Log.d("----->", "DATA FORMATADA " + data);

            return data;
        }else{
            return data;
        }
    }
}
