package dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.User;

/**
 * Created by Marcin on 06/11/2015.
 */
public class DbManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String DATABASE_NAME = "androidquiz";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_USERS = "create table " +
            TABLE_NAME + " (" + COLUMN_ID +
            " integer primary key autoincrement," +
            LOGIN + " text unique," +
            PASSWORD + " text);";

    private static DbManager instance;

    public static synchronized DbManager getInstance(Context context) {
        if (instance == null) {
            instance = new DbManager(context);
        }
        return instance;
    }

    public DbManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table users (" +
                        "id integer primary key autoincrement," +
                        "login text unique," +
                        "email text uniquea," +
                        "password text," +
                        "score int);"
       );

        db.execSQL(
                "create table test(" +
                        "id integer primary key autoincrement," +
                        "question text," +
                        "correctanswer text," +
                        "incorrectanswer1 text," +
                        "incorrectanswer2 text," +
                        "incorrectanswer3 text);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbManager.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    public boolean createUser(User user){
//        SQLiteDatabase database = getWritableDatabase();
//        //String query = "INSERT INTO users (login, password) VALUES (`"+user.getLogin()+"` ,`"+user.getPassword()+"`);";
//        if(checkUserInDb(user.getLogin())) {
//            //database = dbHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put(DbManager.LOGIN, user.getLogin());
//            values.put(DbManager.PASSWORD, user.getPassword());
//            try {
//            database.insertOrThrow(DbManager.TABLE_NAME, null, values);
//           } catch (Exception e) {
//                Log.e("Exception", "SQLException" + String.valueOf(e.getMessage()));
//                e.printStackTrace();
//            }
//
//            return true;
//        }else{
//            return false;
//        }
//    }



}
