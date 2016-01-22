package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dbHelper.DbManager;
import model.Question;
import model.User;

/**
 * Created by Marcin on 13/11/2015.
 */
public class DaoQuestion {

    private DbManager dbManager;
    private SQLiteDatabase database;
    private String[] allColumns = {"id", "question", "correctanswer", "incorrectanswer1", "incorrectanswer2", "incorrectanswer3"};

    public DaoQuestion(Context context) {

        dbManager = new DbManager(context);
    }

    public void open() throws SQLException {
        database = dbManager.getWritableDatabase();
    }

    public void close() {
        dbManager.close();
    }

    public Boolean createQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(allColumns[1], question.getQuestion());
        values.put(allColumns[2], question.getCorrectAnswer());
        values.put(allColumns[3], question.getIncorrAns1());
        values.put(allColumns[4], question.getIncorrAns2());
        values.put(allColumns[5], question.getIncorrAns3());
        try {
            database.insert("test", null, values);
            return true;
        } catch (Exception e) {
            Log.e("Exception", "SQLException" + String.valueOf(e.getMessage()));
            e.printStackTrace();
            return false;
        }
    }

    //ToDO: Create method to get count of questions
    public int getRowCount(){
        //database.beginTransaction();
        //database.execSQL("select count(*) from test");

        //Tymczasowo
        List<Question> questions = new LinkedList<Question>();
        Cursor cursor = database.query("test", allColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setCorrectAnswer(cursor.getString(2));
            question.setIncorrAns1(cursor.getString(3));
            question.setIncorrAns2(cursor.getString(4));
            question.setIncorrAns3(cursor.getString(5));
            questions.add(question);
        }
        return questions.size();

    }

    public List<Question> findAll() {
        List<Question> questions = new LinkedList<Question>();
        Cursor cursor = database.query("test", allColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setCorrectAnswer(cursor.getString(2));
            question.setIncorrAns1(cursor.getString(3));
            question.setIncorrAns2(cursor.getString(4));
            question.setIncorrAns3(cursor.getString(5));
            questions.add(question);
        }
        return questions;
    }
}
