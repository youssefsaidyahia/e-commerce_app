package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class resetPassword extends AppCompatActivity {
    EditText user_name , question1 , question2 ;
    Button changepass;
    String check="",name ;
    DatabaseReference databaseReference ,users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        user_name=findViewById(R.id.namerestore);
        question1=findViewById(R.id.q1);
        question2=findViewById(R.id.q2);
        changepass=findViewById(R.id.change);
        check=getIntent().getExtras().getString("check");


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(check.equals("forget"))
        {
            user_name.setVisibility(View.VISIBLE);
            changepass.setText("Reset");
            changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkdata();
                }
            });

        }
        else if (check.equals("security"))
        {
            databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevelent.userData.getUser_Name());
            user_name.setVisibility(View.GONE);
            changepass.setText("Save");
            getUserInfo();
            changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateData();
                }
            });

        }
    }

    private void checkdata() {
        name = user_name.getText().toString();
        users = FirebaseDatabase.getInstance().getReference().child("Users").child(name);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String key = dataSnapshot.getKey();
                    if (!(name.equals(key))) {
                        Toast.makeText(getApplicationContext(), "your name is not exist,try again", Toast.LENGTH_LONG).show();
                    } else {
                        checkquestions();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkquestions() {
        users.child("Security questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String q1=dataSnapshot.child("Question 1").getValue().toString();
                String q2=dataSnapshot.child("Question 2").getValue().toString();
                String quse1=question1.getText().toString();
                String quse2=question2.getText().toString();

                if (!(quse1.equals(q1))){
                    Toast.makeText(getApplicationContext(), "incorrect answer for question1", Toast.LENGTH_LONG).show();
                }
                else if(!(quse2.equals(q2))){
                    Toast.makeText(getApplicationContext(), "incorrect answer for question2", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent=new Intent(resetPassword.this, changepassword.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void validateData() {
        if(question1.equals("")){
            Toast.makeText(getApplicationContext(),"please , answer qusetion 1",Toast.LENGTH_LONG).show();
        }
        else if(question2.equals("")){
            Toast.makeText(getApplicationContext(),"please , answer qusetion 2",Toast.LENGTH_LONG).show();
        }
        else{
             savetoDatabase();
             }


    }

    private void getUserInfo() {
        databaseReference.child("Security questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    question1.setText(dataSnapshot.child("Question 1").getValue().toString());
                    question1.setText(dataSnapshot.child("Question 2").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void savetoDatabase() {
        Map map=new HashMap();
        map.put("Question 1" ,question1.getText().toString());
        map.put("Question 2",question2.getText().toString());
        databaseReference.child("Security questions").updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "uploaded successfully", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(resetPassword.this, byerHomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
