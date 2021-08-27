package com.example.firebasetwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

public class DisplayActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
private TextView mEmail,mName;
Button button;
private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
private DatabaseReference databaseReference =firebaseDatabase.
        getReference("parent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mEmail=findViewById(R.id.tvEmail);
        mName=findViewById(R.id.tvName);
       button=findViewById(R.id.SignOut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser()!=null){
            GenericTypeIndicator<User>genericTypeIndicator=new
                    GenericTypeIndicator<User>() {
            };
            databaseReference.child(firebaseAuth.getCurrentUser().
                    getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (@NonNull DataSnapshot snapshot) {
                    User user =snapshot.getValue(genericTypeIndicator);
                    mName.setText(user.getName());
                    mEmail.setText(user.getEmail());
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });

        }else{
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
