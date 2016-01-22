package com.example.marcin.simplequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import dao.DaoUser;
import model.User;

public class LoginActivity extends AppCompatActivity {

    public static final User LOGGED_USER = new User();

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.tvEmail);
        mPasswordView = (EditText) findViewById(R.id.tvPassword);

        Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button btnRegister = (Button) findViewById(R.id.login_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMain = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                LoginActivity.this.startActivity(intentMain);

            }
        });
    }

    //mLoginFormView = findViewById(R.id.login_form);
    //mProgressView = findViewById(R.id.login_progress);

    //TODO: Add email to database with automaticly sended information with thanks for the installation.
    public void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        DaoUser daoUser = new DaoUser(getApplicationContext());


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        User user2login = new User();
        user2login.setLogin(email);
        user2login.setPassword(password);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        try {
            daoUser.open();
        } catch (SQLException e) {
            Toast.makeText(this, "Cannot open connection to database.", Toast.LENGTH_SHORT).show();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            if (!daoUser.checkUser(email)) {
                User user = daoUser.getUser(email);
                if (user.getPassword().equals(user2login.getPassword())) {
                    Intent intentMain = new Intent(LoginActivity.this,
                            MainActivity.class);
                    intentMain.putExtra("user", user);
                    LoginActivity.this.startActivity(intentMain);
                    Toast.makeText(this, "Successfull loged in.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Wrong login or password.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "There is no user registered with that login.", Toast.LENGTH_SHORT).show();
            }

        }

        daoUser.close();

    }

    public void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        DaoUser daoUser = new DaoUser(getApplicationContext());


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        User user = new User();
        user.setLogin(email);
        user.setPassword(password);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

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

            } else {
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

