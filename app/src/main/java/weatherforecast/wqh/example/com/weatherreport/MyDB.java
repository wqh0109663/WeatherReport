package weatherforecast.wqh.example.com.weatherreport;

/**
 * Created by wqh on 2017/11/1.
 */



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 SQLite数据库操作管理
 * **/


public class MyDB extends SQLiteOpenHelper {
    //private static String DB_NAME="My_DB.db";
    private static int DB_VERSION=2;
    private SQLiteDatabase db;
    public MyDB(Context context ,String DB_NAME){
        super(context,DB_NAME,null,DB_VERSION);
        db=getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
    /**执行SQLite数据库链接**/
    public SQLiteDatabase openConnection(){
        if(!db.isOpen())
        {
            db=getWritableDatabase();
        }
        return db;
    }
    /**关闭SQLite数据库链接操作**/
    public void closeConnection(){
        try{
            if(db!=null&&db.isOpen())
                db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**创建表**/
    public boolean  creatTable(String createTableSql)
    {
        try{if (!db.isOpen())
            openConnection();
            db.execSQL(createTableSql);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    /**添加操作**/
    public boolean save(String tableName, ContentValues values)
    {
        try{
            openConnection();
            db.insert(tableName,null,values);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    /**更新操作**/

    public boolean update(String table,ContentValues values,String whereClause,String []whereArgs)
    {
        try{
            openConnection();
            db.update(table,values,whereClause,whereArgs);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    /**删除**/
    public boolean delete(String table,String deleteSql,String obj[])
    {
        try {
            openConnection();
            db.delete(table,deleteSql,obj);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    /**查询操作**/
    public Cursor find (String findSql,String obj[])
    {
        try {
            openConnection();
            Cursor cursor=db.rawQuery(findSql,obj);
            return cursor;
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    /**判断表是否存在**/
    public boolean isTableExits(String tablename){
        try {
            openConnection();
            String str="select count(*)*count from "+tablename;
            db.rawQuery(str,null).close();
        }catch (Exception ex)
        {
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
}
