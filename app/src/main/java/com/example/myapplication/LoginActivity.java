package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.apis.ApiService;
import com.example.myapplication.components.LoadingDialog;
import com.example.myapplication.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    AppCompatButton loginButton;
    EditText email;
    EditText password;
    boolean passwordVisible;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.drawable.background) ;
        setContentView(R.layout.activity_login);
        signUp = findViewById(R.id.login_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        loadingDialog = new LoadingDialog(LoginActivity.this);

        password = findViewById(R.id.login_password);
        email = findViewById(R.id.login_email);
        setUp();

    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void login() {
        String mail = email.getText().toString();
        String pWord = password.getText().toString();
        if(isValidEmail(mail) == false){
            email.setError("This mail isn't existed.");
            return;
        } else if(TextUtils.isEmpty(pWord) || pWord.length() < 8){
            password.setError("Password length must be greater than 8 digits");
            return;
        }
        JsonObject request = new JsonObject();
        request.addProperty("email", mail);
        request.addProperty("password", pWord);
        loadingDialog.startLoading();
        ApiService.apiService.login(request).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               if(response.code() == 200){
                   SharedPreferences settings = getApplicationContext().getSharedPreferences("MYAPPLICATION", Context.MODE_PRIVATE);
                   SharedPreferences.Editor prefsEditor = settings.edit();
                   Gson gson = new Gson();
                   User emp = gson.fromJson(response.body().getAsJsonObject("data"), User.class);
                   prefsEditor.putString("user", emp.toString());
                   prefsEditor.apply();
                   System.out.println(getPreferences(MODE_PRIVATE).getString("user","defaultStringIfNothingFound"));
                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();
               } else {
                   Toast.makeText(LoginActivity.this, "Either email or password is incorrect", Toast.LENGTH_LONG);
               }
               loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void setUp() {
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if(passwordVisible){
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_remove_red_eye_24,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}