package com.example.vegproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private Button signUp;
    private TextInputEditText name,emailaddress,contact,address,password,confirmpassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView textSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textSignin = findViewById(R.id.textsignin);
        signUp = findViewById(R.id.signup);
        progressBar = findViewById(R.id.progressbar);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        emailaddress = findViewById(R.id.emailaddress);
        contact = findViewById(R.id.mobno);
        address = findViewById(R.id.address);
        confirmpassword = findViewById(R.id.confirmpassword);
        mAuth = FirebaseAuth.getInstance();
        textSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName = name.getText().toString().trim();
                String sEmail = emailaddress.getText().toString().trim();
                String sContact = contact.getText().toString().trim();
                String sAddress = address.getText().toString().trim();
                String sPassword = password.getText().toString().trim();
                String sConfirm = confirmpassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!(sName.isEmpty()||sEmail.isEmpty()||sContact.isEmpty()||sAddress.isEmpty()||sPassword.isEmpty()||sConfirm.isEmpty()))
                {
                    if(sEmail.matches(emailPattern))
                    {
                        if (sContact.length()==10)
                        {
                            if(sPassword.equals(sConfirm))
                            {
                                mAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if(task.isSuccessful())
                                                {
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    signUp.setVisibility(View.GONE);
                                                    new Thread(() -> {
                                                        try
                                                        {
                                                            Thread.sleep(2000);
                                                        }
                                                        catch (InterruptedException e)
                                                        {
                                                            e.printStackTrace();
                                                        }
                                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                                        SignUp.this.finish();
                                                    }).start();

                                                }
                                                else
                                                {
                                                    Toast.makeText(SignUp.this, "Fail to register user "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                            }
                            else
                            {
                                Toast.makeText(SignUp.this, "Password and Confirm Password does not matches", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Enter 10 digit Contact Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}