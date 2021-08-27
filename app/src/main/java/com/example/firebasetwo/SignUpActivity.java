package com.example.firebasetwo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private EditText mName, mPass, mEmail;
    private final FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference=firebaseDatabase.
            getReference("parent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEmail = findViewById(R.id.etEmail);
        mName = findViewById(R.id.etName);
        mPass = findViewById(R.id.etPassword);
        Button signUp = findViewById(R.id.Signuptwo);


       signUp.setOnClickListener(v -> {
      if(VALIDATE()){
          firebaseAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPass.getText().toString())
                  .addOnCompleteListener(task -> {
if (task.isSuccessful()){
User user=new User(
   mName.getText().toString(),
   mEmail.getText().toString()
);
databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
   .setValue(user)
   .addOnCompleteListener(task1 -> {
       Intent intent=new Intent(getBaseContext(), DisplayActivity.class);
       startActivity(intent);


   });

}
                  });


}
       });
    }

    private boolean VALIDATE() {
        boolean isVlid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
mEmail.setError("Invalid Email");
isVlid=false;

        }if (mPass.getText().toString().length()<4){
            mPass.setError("pass is too short");
            isVlid =false;
        }
    return isVlid;
    }
}