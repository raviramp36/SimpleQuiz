package com.example.marcin.simplequiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dao.DaoQuestion;
import dao.DaoUser;
import model.Question;
import model.User;

/**
 * Created by Marcin on 14/11/2015.
 */
public class TestActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private TextView tv;
    private int iterator = 0;
    private List<Question> listOfQuestions;
    private ArrayList<String> listOfAnswers;
    private User user;
    private int score;
    private TextView tvNumOfQuestion;
    private int numberOfActualQuestion;
    private int totalQuestionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        user = (User) getIntent().getSerializableExtra("user");
        score = 0;
        user.setScore(score);

        DaoUser daoUser = new DaoUser(getApplicationContext());
        try {
            daoUser.open();
        } catch (SQLException e) {

        }

        daoUser.updateUserScore(user);

        tv = (TextView) findViewById(R.id.textView);
        tvNumOfQuestion = (TextView) findViewById(R.id.numOfQuestion);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        numberOfActualQuestion = 0;

        DaoQuestion daoQuestion = new DaoQuestion(getApplicationContext());

        try {
            daoQuestion.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listOfQuestions = daoQuestion.findAll();

        totalQuestionNumber = listOfQuestions.size();
        tvNumOfQuestion.setText(numberOfActualQuestion + "/" + totalQuestionNumber);

        if (listOfQuestions.size() != 0 && numberOfActualQuestion <= listOfQuestions.size()) {
            Collections.shuffle(listOfQuestions);
            listOfAnswers = new ArrayList<>();

            listOfAnswers.add(listOfQuestions.get(iterator).getCorrectAnswer());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns1());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns2());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns3());

            Collections.shuffle(listOfAnswers);
            tv.setText(listOfQuestions.get(iterator).getQuestion());

            btn1.setText(listOfAnswers.get(0));
            btn2.setText(listOfAnswers.get(1));
            btn3.setText(listOfAnswers.get(2));
            btn4.setText(listOfAnswers.get(3));

            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);

        } else {
            Toast.makeText(this, "There is no question now!", Toast.LENGTH_SHORT).show();
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer((String) btn1.getText(), listOfQuestions);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer((String) btn2.getText(), listOfQuestions);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer((String) btn3.getText(), listOfQuestions);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer((String) btn4.getText(), listOfQuestions);
            }
        });

    }

    public void checkAnswer(String string, List<Question> listOfQuestion) {
        String corrAnsw = listOfQuestion.get(iterator).getCorrectAnswer();

        listOfAnswers.removeAll(listOfAnswers);

        if (corrAnsw.equals(string)) {
            iterator += 1;
            score += 1;

        } else {
            iterator += 1;
            score += 0;
        }

        if (iterator >= listOfQuestion.size()) {
            iterator = 0;
            tv.setText("");
            btn1.setText("");
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("This is end of the quiz\nYour score is = " + score);
            alertDialogBuilder.setPositiveButton("okey", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    user.setScore(score);
                    DaoUser daoUser = new DaoUser(getApplicationContext());
                    try {
                        daoUser.open();
                        daoUser.updateUserScore(user);

                        Intent intentMain = new Intent(TestActivity.this,
                                MainActivity.class);
                        intentMain.putExtra("user", user);
                        TestActivity.this.startActivity(intentMain);
                        daoUser.close();
                    } catch (SQLException e) {

                    }

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else {

            numberOfActualQuestion += 1;
            listOfAnswers.add(listOfQuestions.get(iterator).getCorrectAnswer());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns1());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns2());
            listOfAnswers.add(listOfQuestions.get(iterator).getIncorrAns3());

            Collections.shuffle(listOfAnswers);
            tv.setText(listOfQuestions.get(iterator).getQuestion());
            tvNumOfQuestion.setText(numberOfActualQuestion + "/" + totalQuestionNumber);

            btn1.setText(listOfAnswers.get(0));
            btn2.setText(listOfAnswers.get(1));
            btn3.setText(listOfAnswers.get(2));
            btn4.setText(listOfAnswers.get(3));
        }
    }
}
