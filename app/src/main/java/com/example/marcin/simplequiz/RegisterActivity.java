package com.example.marcin.simplequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import dao.DaoUser;
import model.User;

/**
 * Created by Marcin on 22/01/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    TextView tvLogged;
    EditText etLogin;
    EditText etEmail;
    EditText etPassword;
    EditText etRepPass;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogged = (TextView)findViewById(R.id.tvLogged);
        etLogin = (EditText)findViewById(R.id.tvLogin);
        etEmail = (EditText)findViewById(R.id.tvEmail);
        etPassword = (EditText)findViewById(R.id.tvPassword);
        etRepPass = (EditText)findViewById(R.id.tvRepPass);

        btnRegister = (Button)findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptRegister();

                if(etLogin.getText().toString().isEmpty() == false) {

                    try {
                        DaoUser daoUser = new DaoUser(getApplicationContext());
                        daoUser.open();
                        User user = daoUser.getUser(etLogin.getText().toString());
                        Intent intentMain = new Intent(RegisterActivity.this,
                                MainActivity.class);
                        intentMain.putExtra("user", user);
                        RegisterActivity.this.startActivity(intentMain);
                        daoUser.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please fill empty fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void attemptRegister() {
        // Reset errors.

        etEmail.setError(null);
        etPassword.setError(null);


        DaoUser daoUser = new DaoUser(getApplicationContext());


        // Store values at the time of the login attempt.
        String login = etLogin.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password) && password.equals(etRepPass.getText().toString())) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        }
         else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        try {
            daoUser.open();
        } catch (SQLException e) {
            Toast.makeText(this, "Cannot open connection to database.", Toast.LENGTH_SHORT).show();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            if (daoUser.checkUser(email)) {
                if (daoUser.createUser(user)) {
                    Toast.makeText(this, "User successfull created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not today!", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "User already exist in database.", Toast.LENGTH_SHORT).show();
            }

        }

        daoUser.close();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
