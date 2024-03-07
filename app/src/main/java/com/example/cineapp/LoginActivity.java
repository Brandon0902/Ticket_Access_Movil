package com.example.cineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.io.IOException;

import api.TicketaccssAppService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnEntrar;

    private TicketaccssAppService cineAppService;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        initRetrofitClient();

        btnEntrar.setOnClickListener(view-> {

            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            cineAppService.login(email, password)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.isSuccessful()){
                                    String token = response.body().string(); // Suponiendo que el token se devuelve como una cadena en el cuerpo de la respuesta
                                    saveTokenToSharedPreferences(token);
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
    }

    private void initRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.13:8000/")
                .build();

        cineAppService = retrofit.create(TicketaccssAppService.class);


    }

    private void showErrorMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void navigateToMenuActivity(){
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        startActivity(menuActivityIntent);
    }

    private void saveTokenToSharedPreferences(String token) {
        // Obtener una referencia al SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // Obtener un editor para modificar el SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Guardar el token en el SharedPreferences con una clave "token"
        editor.putString("token", token);
        // Aplicar los cambios
        editor.apply();
    }
}