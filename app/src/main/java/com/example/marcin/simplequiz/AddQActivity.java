package com.example.marcin.simplequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import dao.DaoQuestion;
import model.Question;
import model.User;

/**
 * Created by Marcin on 14/11/2015.
 */
public class AddQActivity extends AppCompatActivity {

    private TextView textQuestion;
    private TextView textCorr;
    private  TextView textInCorr1;
    private TextView textInCorr2;
    private TextView textInCorr3;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);

        user = (User)getIntent().getSerializableExtra("user");
        textQuestion = (TextView)findViewById(R.id.fieldQuestiono);
        textCorr = (TextView)findViewById(R.id.fieldCorr);
        textInCorr1 = (TextView)findViewById(R.id.fieldInCorr1);
        textInCorr2 = (TextView)findViewById(R.id.fieldInCorr2);
        textInCorr3 = (TextView)findViewById(R.id.fieldIncorr3);
        Button btnAccept = (Button)findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();


            }
        });
    }

    public void addQuestion(){
        Question question = new Question();

        String[] text = new String[5];
        boolean cancel = false;
        View focusView = null;

        text[0] = textQuestion.getText().toString();
        text[1] = textCorr.getText().toString();
        text[2] = textInCorr1.getText().toString();
        text[3] = textInCorr2.getText().toString();
        text[4] = textInCorr3.getText().toString();

        if (text[0].isEmpty()) {
            textQuestion.setError(getString(R.string.error_field_required));
            focusView = textQuestion;
            cancel = true;
        }else if(text[1].isEmpty()){
            textCorr.setError(getString(R.string.error_field_required));
            focusView = textCorr;
            cancel = true;
        }else if(text[2].isEmpty()){
            textInCorr1.setError(getString(R.string.error_field_required));
            focusView = textInCorr1;
            cancel = true;
        }else if(text[3].isEmpty()){
            textInCorr2.setError(getString(R.string.error_field_required));
            focusView = textInCorr2;
            cancel = true;
        }else if(text[4].isEmpty()){
            textInCorr3.setError(getString(R.string.error_field_required));
            focusView = textInCorr3;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();

        }else{
            question.setQuestion(text[0]);
            question.setCorrectAnswer(text[1]);
            question.setIncorrAns1(text[2]);
            question.setIncorrAns2(text[3]);
            question.setIncorrAns3(text[4]);

            DaoQuestion daoQuestion = new DaoQuestion(getApplicationContext());
            try {
                daoQuestion.open();
                daoQuestion.createQuestion(question);
                daoQuestion.close();

                Intent intentMain = new Intent(AddQActivity.this,
                        MainActivity.class);
                intentMain.putExtra("user", user);
                AddQActivity.this.startActivity(intentMain);
                Toast.makeText(this, "Question added.", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong."+e.getMessage(), Toast.LENGTH_LONG).show();
                //e.printStackTrace();
            }

        }
    }
}
