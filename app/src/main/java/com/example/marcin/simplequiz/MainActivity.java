package com.example.marcin.simplequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.User;

/**
 * Created by Marcin on 13/11/2015.
 */
public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User)getIntent().getSerializableExtra("user");
        TextView tvLogged = (TextView)findViewById(R.id.tvLogged);
        tvLogged.setText("Logged as: " + user.getLogin());
        Button btnAddQ = (Button)findViewById(R.id.buttonAddQ);
        Button btnLogut = (Button)findViewById(R.id.btnLogout);
        Button btnStart = (Button)findViewById(R.id.btnStart);

        btnAddQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMain = new Intent(MainActivity.this,
                        AddQActivity.class);
                intentMain.putExtra("user", user);
                MainActivity.this.startActivity(intentMain);


            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMain = new Intent(MainActivity.this,
                        TestActivity.class);
                intentMain.putExtra("user", user);
                MainActivity.this.startActivity(intentMain);


            }
        });



        btnLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMain = new Intent(MainActivity.this,
                        LoginActivity.class);
                MainActivity.this.startActivity(intentMain);


            }
        });
    }


}
