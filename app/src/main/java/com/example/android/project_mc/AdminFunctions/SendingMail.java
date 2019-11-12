package com.example.android.project_mc.AdminFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.project_mc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendingMail extends AppCompatActivity {
    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextMessage; 
    private DatabaseReference databaseReference;
    String userName , ship;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_mail);
        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
        Button buttonSend = findViewById(R.id.button_send);
        userName=getIntent().getExtras().getString("username");
        ship=getIntent().getExtras().getString("shipped");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders").child(userName);
        loadData();
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void loadData() {
        if(ship.equals("state")) {
            mEditTextSubject.setText("OrderDetails");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mEditTextTo.setText(dataSnapshot.child("Email").getValue().toString());
                        mEditTextMessage.setText("your order has been approved by the admin with total price" + dataSnapshot.child("Total_Price").getValue().toString() + "and it will be arrived in" + dataSnapshot.child("Address").getValue().toString() + "between two to four days ,  Thank you for trust");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if (ship.equals("not accept"))
        {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mEditTextTo.setText(dataSnapshot.child("Email").getValue().toString());

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mEditTextSubject.setText("Ordercanceled");
            mEditTextMessage.setText("admin canceled your order , please try again");
        }
        databaseReference.removeValue();
    }

    private void sendMail() {
        String Email=mEditTextTo.getText().toString();
        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, Email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
