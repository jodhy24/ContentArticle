package com.example.contentarticle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentarticle.R;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    EditText email, password1;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);

        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.password1);
        login = (Button) findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                // Destroy Aplikasi
                finish();
                // bikin TOAST
                Toast.makeText(LoginActivity.this, "Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("") || password1.getText().toString().equals("") ) {
                    Toast.makeText(LoginActivity.this, "Harap isi username Dan password", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent (LoginActivity.this, HomeActivity.class));
                    // Destroy Aplikasi
                    finish();

                    // bikin TOAST
                    Toast.makeText(LoginActivity.this, "Selamat anda berhasil login", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
