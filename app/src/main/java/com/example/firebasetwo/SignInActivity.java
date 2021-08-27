package com.example.firebasetwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
private EditText email,pass;
private Button SignIn;
private final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
   email=findViewById(R.id.etEmail1);
   pass=findViewById(R.id.etPassword1);
   SignIn=findViewById(R.id.SignInTwo);
   SignIn.setOnClickListener(v -> firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
           pass.getText().toString()).
            addOnCompleteListener(task -> {
                Intent intent=new Intent(getBaseContext(),DisplayActivity.class);
                startActivity(intent);

            }).addOnFailureListener(e -> e.printStackTrace()));
    }
}