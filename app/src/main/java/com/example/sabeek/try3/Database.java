package com.example.sabeek.try3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SABEEK on 6/13/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String Databasename="student.db";
    private static final String tablename="studentdetals";
    private static final int version=3;
    private static final String id="id";
    private static final String name="name";
    private static final String REASON="reason";
    private static final String TIME="age";
    private static final String Entered="gjg";

    private static final String droptable="DROP TABLE IF EXISTS "+tablename;
    private static final String select="SELECT * FROM "+tablename;
    private Context context;
    //C:\Users\SABEEK\AppData\Local\Android\Sdk
    private static final String createtable="CREATE TABLE "+tablename+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+REASON+" VARCHAR(255), "+name+" VARCHAR(255), "+TIME+" VARCHAR(255), "+Entered+" VARCHAR(255) ); ";
   // private static final String createtable="CREATE TABLE "+tablename+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255), " +age+ " VARCHAR(223), "+gender+" VARCHAR(15) ); ";
    // private static final String createtable="CREATE TABLE "+tablename+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255), " +age+ " INTEGER, "+gender+" VARCHAR(15) ); ";
    // private static final String createtable="CREATE TABLE"+tablename+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255),"+age+"INTEGER ); ");
    public Database(Context context) {

        super(context, Databasename, null, version);
        this.context=context;
        // contexts=context;

        //  Toast.makeText(context,"what is1",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Toast.makeText(context,"on create is calked",Toast.LENGTH_SHORT).show();
        //System.out.println("sabeek");
        try {
            db.execSQL(createtable);
            Toast.makeText(context,"on create is called",Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {

            Toast.makeText(context,"ist one Exception "+ e,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Toast.makeText(context,"table drpeed",Toast.LENGTH_LONG).show();
            db.execSQL(droptable);
            onCreate(db);

        }
        catch (Exception e)
        {

            Toast.makeText(context,"2nd one Exception "+ e,Toast.LENGTH_LONG).show();
        }


    }
    public long insert(String reason,String time,String spent,String enter)
    {
        SQLiteDatabase sd=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(REASON,reason);
        cv.put(name,time);
        cv.put(TIME,spent);
        cv.put(Entered,enter);

        //cv.put(age,ages);
        //cv.put(gender,genders);
        long rowid=sd.insert(tablename,null,cv);//succesfully store hoile eka value return kore long type er and if not succeful then retun -1
        return rowid;
    }
    public Cursor getData()
    {
        SQLiteDatabase sd=this.getWritableDatabase();
        Cursor cr=sd.rawQuery(select,null);//shobgula result return korrbe ,
        //shobgula result cursor er modhe save kore rakhlam
        return cr;


    }
    public boolean update(String ids,String names,String ages,String genders)
    {

        SQLiteDatabase sd=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(id,ids);
        cv.put(name,names);
        cv.put(TIME,ages);
        cv.put(REASON,genders);
        sd.update(tablename,cv,id+" = ?",new String[]{ids});
        return true;

    }
    public int delete(String ids)
    {

        SQLiteDatabase sd=this.getWritableDatabase();
        return  sd.delete(tablename,id+" = ?",new String[]{ids});

    }
    public void deleteall()
    {
        SQLiteDatabase sd=this.getWritableDatabase();
        sd.delete(tablename, null, null);
    }
}


