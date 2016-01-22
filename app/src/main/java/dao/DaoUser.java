package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import dbHelper.DbManager;
import model.User;

/**
 * Created by Marcin on 09/11/2015.
 */
public class DaoUser {

    private DbManager dbManager;
    private SQLiteDatabase database;
    private String[] allColumns = {DbManager.COLUMN_ID, DbManager.LOGIN, DbManager.PASSWORD};

    public DaoUser(Context context) {

        dbManager = new DbManager(context);
    }

    public void open() throws SQLException {
        database = dbManager.getWritableDatabase();
    }

    public void close() {
        dbManager.close();
    }

    public Boolean createUser(User user) {
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("score", 0);
        try {
//            database.insertOrThrow("users", null, values);
            database.insert("users", null, values);
            return true;
        } catch (Exception e) {
            Log.e("Exception", "SQLException" + String.valueOf(e.getMessage()));
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAll() {
        List<User> words = new LinkedList<User>();
        String[] columns = {"id", "login", "email", "password"};
        Cursor cursor = database.query("users", columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User word = new User();
            word.setId(cursor.getInt(0));
            word.setLogin(cursor.getString(1));
            word.setEmail(cursor.getString(2));
            word.setPassword(cursor.getString(3));
            words.add(word);
        }
        return words;
    }

    public User getUser(String login) {

        String[] columns = {"id", "login", "email", "password", "score"};
        Cursor cursor = null;
        User user = new User();

        String[] whereArgs = new String[] {login};

        try {
            //cursor = database.rawQuery("select * from users where login = '" + login + "'", null);
            cursor = database.query("users", columns, "login = ?", whereArgs, null, null, null);
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                user.setId(cursor.getInt(0));
                user.setLogin(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setScore(cursor.getInt(4));
            }

            return user;
        } finally {
            //cursor.close();
        }


       // }
    }

    public boolean updateUserScore(User user){
        try {
            String[] columns = {"id", "login", "email", "password", "score"};
            ContentValues cv = new ContentValues();
            cv.put(columns[0], user.getId());
            cv.put(columns[1], user.getLogin());
            cv.put(columns[2], user.getEmail());
            cv.put(columns[3], user.getPassword());
            cv.put(columns[4], user.getScore());
            database.update("users", cv, "id="+user.getId(), null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean checkUser(String login) {

        Cursor cursor = null;
        try {

            cursor = database.rawQuery("select * from users where login = '" + login + "'", null);

            if (cursor.getCount() > 0) {
                return false;
            }else{
                return true;
            }
        } finally {

            cursor.close();
        }
    }

    public boolean checkUserInDb(String login) {

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("select login from users where login = '" + login + "'", null);
        } catch (SQLiteException e) {
            Log.e("Exception", "SQLException" + String.valueOf(e.getMessage()));
        }
        if (cursor != null) {
            return false;
        } else {
            return true;
        }
    }


}
