package com.example.language.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srh Dp on 30-Jun-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME="dictionary.sqlite";
    public static final String DBLOCATION="C:\\Users\\Nastya\\Desktop\\uni\\4\\Language\\app\\src\\main\\assets\\dictionary.sqlite";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase(){
        String dbPath=mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase!=null && mDatabase.isOpen()){
            return;
        }
        mDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase(){
        if(mDatabase !=null){
            mDatabase.close();
        }
    }

    public List<DictionaryModel> getListWord(String wordSearch){
        DictionaryModel dictionaryModel=null;
        List<DictionaryModel> dictionaryModelList=new ArrayList<>();
        openDatabase();
        String[] args={"%"+wordSearch+"%"};

        Cursor cursor=mDatabase.rawQuery("Select * From tblWord Where word Like ?",args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            dictionaryModel=new DictionaryModel(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return dictionaryModelList;
    }

}
