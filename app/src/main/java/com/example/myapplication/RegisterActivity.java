package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.apis.ApiService;
import com.example.myapplication.components.LoadingDialog;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ImageButton backButton;
    AppCompatButton registerButton;
    EditText fullName;
    EditText email;
    EditText password;
    LoadingDialog loadingDialog;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.drawable.background) ;
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.register_full_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        loadingDialog = new LoadingDialog(RegisterActivity.this);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        setUp();
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

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void createAccount() {
        String fName = fullName.getText().toString();
        String mail = email.getText().toString();
        String pWord = password.getText().toString();

        if(TextUtils.isEmpty(fName)){
            fullName.setError("Name is incorrect. Try again");
            return;
        } else if(!isValidEmail(mail)){
            email.setError("Email is incorrect. Try again");
            return;
        } else if(TextUtils.isEmpty(pWord) || pWord.length() < 8){
            password.setError("Password length must be greater than 8 digits.");
            return;
        }
        loadingDialog.startLoading();
        User request = new User(fName, pWord, mail, false);
        ApiService.apiService.register(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loadingDialog.dismiss();
                System.out.println();
                if(response.code() == 200){
                    Intent myIntent = new Intent(RegisterActivity.this, OTPActivity.class).putExtra("info", new Gson().toJson(request));
                    startActivity(myIntent);
                } else {
                    Toast.makeText(RegisterActivity.this,"Email is existed. Try another one",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}