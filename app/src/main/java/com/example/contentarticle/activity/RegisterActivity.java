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
import com.example.contentarticle.model.retrofit.ResponseLogin;
import com.example.contentarticle.model.retrofit.ResponseRegister;
import com.example.contentarticle.network.InitRetrofit;
import com.example.contentarticle.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    Button register;
    EditText username, password1, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.password1);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("") || password1.getText().toString().equals("") ||
                        username.getText().toString().equals("") || phone.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Harap Lengkapi Data", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void register() {
        RestApi restApi = InitRetrofit.getInstance();

        Call<ResponseRegister> registerCall = restApi.registerUser(
                username.getText().toString(),
                email.getText().toString(),
                phone.getText().toString(),
                password1.getText().toString()
        );

        registerCall.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()) {

                    String result = response.body().getResponse();

                    if (result.equals("success")) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();

                        Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else if (result.equals("failed")) {
                        Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
