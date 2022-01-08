package com.example.vegproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private TextView textSignUp;
    private Button SignIn;
    private TextInputEditText EmailAddress,Password;
    private FirebaseAuth mAuth;
    private ProgressBar signInProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        textSignUp = findViewById(R.id.textsignup);
        EmailAddress = findViewById(R.id.signinemailaddress);
        Password = findViewById(R.id.signinpassword);
        signInProgress = findViewById(R.id.signinprogressbar);
        SignIn = findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = EmailAddress.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!(username.isEmpty()||password.isEmpty()))
                {
                    if(username.matches(emailPattern))
                    {
                        mAuth.signInWithEmailAndPassword(username,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            signInProgress.setVisibility(View.VISIBLE);
                                            SignIn.setVisibility(View.GONE);
                                            new Thread(() -> {
                                                try
                                                {
                                                    Thread.sleep(1000);
                                                }
                                                catch (InterruptedException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                startActivity(new Intent(SignIn.this, MainActivity.class));
                                                SignIn.this.finish();
                                            }).start();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Email Pattern Wrong", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignIn.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent i = new Intent(SignIn.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}