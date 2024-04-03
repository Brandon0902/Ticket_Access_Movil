package com.example.cineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import api.TicketaccssAppService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class resetpassword extends AppCompatActivity {
    private EditText emailReset;
    private Button btnReset;

    private TicketaccssAppService TicketaccssAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        emailReset = findViewById(R.id.emailReset);
        btnReset = findViewById(R.id.btnReset);

        initRetrofitClient();

        btnReset.setOnClickListener(view-> {
            String email = emailReset.getText().toString();

            TicketaccssAppService.resetpassword(email).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(resetpassword.this, "Se ha enviado un correo para restablecer la contraseña.", Toast.LENGTH_SHORT).show();
                        navigateToLoginActivity();
                    } else {
                        try {
                            String errorMessage = response.errorBody().string();
                            showErrorMessage(errorMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                            showErrorMessage(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    showErrorMessage(t.getMessage());
                }
            });
        });
    }

    private void initRetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://techno-pf-bw-sunset.trycloudflare.com/")
                .build();

        TicketaccssAppService = retrofit.create(TicketaccssAppService.class);
    }

    private void showErrorMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToLoginActivity(){
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
        finish();
    }
}