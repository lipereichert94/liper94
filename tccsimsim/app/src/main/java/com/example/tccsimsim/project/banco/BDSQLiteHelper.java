package com.example.tccsimsim.project.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projeto3.model.DayTrade;
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
    public void limpatabela(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABELA_USUARIO);
        db.close();
    }

    public void addUsuario(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATA, dt.getData());
        values.put(VALOR, new Integer(dt.getValor()));
        values.put(CONTRATO, new Integer(dt.getContrato()));
        db.insert(TABELA_DAYTRADE, null, values);
        db.close();
    }

    public DayTrade getDayTrade(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_DAYTRADE, // a. tabela
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
            DayTrade dt = cursorToDayTrade(cursor);
            return dt;
        }
    }

    private DayTrade cursorToDayTrade(Cursor cursor) {
        DayTrade dt = new DayTrade();
        dt.setId(Integer.parseInt(cursor.getString(0)));
        dt.setData(cursor.getString(1));
        dt.setValor(Integer.parseInt(cursor.getString(2)));
        dt.setContrato(Integer.parseInt(cursor.getString(3)));
        dt.setObs(cursor.getString(4));
        dt.setFoto(cursor.getString(5));
        return dt;
    }

    public ArrayList<DayTrade> getAllDayTrades() {
        ArrayList<DayTrade> listaDayTrade = new ArrayList<DayTrade>();
        String query = "SELECT * FROM " + TABELA_DAYTRADE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DayTrade dt = cursorToDayTrade(cursor);
                listaDayTrade.add(dt);
            } while (cursor.moveToNext());
        }
        return listaDayTrade;
    }

    public int updateDayTrade(DayTrade dt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATA, dt.getData());
        values.put(VALOR, new Integer(dt.getValor()));
        values.put(CONTRATO, new Integer(dt.getContrato()));
        values.put(OBS, dt.getObs());
        values.put(FOTO,dt.getFoto());
        int i = db.update(TABELA_DAYTRADE, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(dt.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteDayTrade(DayTrade dt) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_DAYTRADE, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(dt.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas excluídas
    }
}
