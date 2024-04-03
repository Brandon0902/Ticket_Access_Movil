package com.example.cineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    private EditText Editemail;
    private EditText Editpassword;
    private Button btnEntrar;

    private Button btnRecuperarContrasena;

    private TicketaccssAppService TicketaccssAppService;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Editemail = findViewById(R.id.Editemail);
        Editpassword= findViewById(R.id.Editpassword);
        btnEntrar = findViewById(R.id.btnReset);
        btnRecuperarContrasena = findViewById(R.id.btnRecuperarContrasena);

        initRetrofitClient();

        btnEntrar.setOnClickListener(view-> {

            String email = Editemail.getText().toString();
            String password = Editpassword.getText().toString();

            TicketaccssAppService.login(email, password)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.isSuccessful()){
                                    navigateToMenuActivity();
                                }else {
                                    String errorMessage = response.errorBody().string();
                                    showErrorMessage(errorMessage);
                                }

                            } catch (IOException exception){
                                exception.printStackTrace();
                                showErrorMessage(exception.getMessage());
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            showErrorMessage(t.getMessage());
                        }
                    });

        });

        btnRecuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para cambiar de actividad
                Intent intent = new Intent(LoginActivity.this, resetpassword.class);

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });
    }

    private void initRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://techno-pf-bw-sunset.trycloudflare.com/")
                .build();

        TicketaccssAppService = retrofit.create(TicketaccssAppService.class);


    }

    private void showErrorMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void navigateToMenuActivity(){
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        startActivity(menuActivityIntent);
    }
}