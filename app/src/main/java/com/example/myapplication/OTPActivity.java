package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.myapplication.apis.ApiService;
import com.example.myapplication.components.LoadingDialog;
import com.example.myapplication.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    ImageButton backButton;
    TextView email;
    EditText inputCode1, inputCode2, inputCode3, inputCode4;
    User user;
    LoadingDialog loadingDialog;
    TextView resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        setContentView(R.layout.activity_otpactivity);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String value = getIntent().getExtras().getString("info");
        user = new Gson().fromJson(value, User.class);

        inputCode1 = findViewById(R.id.code_1);
        inputCode2 = findViewById(R.id.code_2);
        inputCode3 = findViewById(R.id.code_3);
        inputCode4 = findViewById(R.id.code_4);

        email = findViewById(R.id.otp_email);
        email.setText(getString(R.string.verification_subtitle) + " " + user.getEmail());

        resend = findViewById(R.id.resendCode);
        loadingDialog = new LoadingDialog(OTPActivity.this);
        setupOTPInput();
        setUp();
    }

    void setUp() {
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoading();
                ApiService.apiService.register(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        loadingDialog.dismiss();
                        System.out.println();
                        if (response.code() == 200) {
                            Toast.makeText(OTPActivity.this, "Code has been sent. Check your mail", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OTPActivity.this, "Email is existed. Try another one", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println(t.toString());
                        Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setupOTPInput() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    inputCode1.requestFocus();
                }
            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    inputCode1.requestFocus();
                }
            }
        });

        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    inputCode2.requestFocus();
                }
            }
        });

        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    inputCode3.requestFocus();
                } else {
                    loadingDialog.startLoading();
                    String code = inputCode1.getText().toString() + inputCode2.getText().toString()
                            + inputCode3.getText().toString() + inputCode4.getText().toString();
                    JsonObject request = new JsonObject();
                    request.addProperty("fullName", user.getFullName());
                    request.addProperty("email", user.getEmail());
                    request.addProperty("password", user.getPassword());
                    request.addProperty("isSeller", user.isSeller());
                    request.addProperty("otp", code);
                    ApiService.apiService.validateOTP(request).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.code() == 200) {
                                Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OTPActivity.this, "OTP is invalid. Try again", Toast.LENGTH_LONG);
                            }
                            loadingDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            loadingDialog.dismiss();
                            System.out.println(t.toString());
                        }
                    });
                }
            }
        });


    }
}