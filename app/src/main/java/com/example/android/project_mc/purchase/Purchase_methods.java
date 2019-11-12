package com.example.android.project_mc.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.project_mc.buyer.byerHomeActivity;
import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Purchase_methods extends AppCompatActivity {
    private RadioGroup radiopurchaseGroup;
    private RadioButton radiopurchaseButton;
    private Button contiu;
    private DatabaseReference databaseReference;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_methods);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders");
        ddListenerOnButton();


    }

    private void ddListenerOnButton() {
        radiopurchaseGroup =findViewById(R.id.purchaseMethods);
        contiu = findViewById(R.id.continu);
        contiu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radiopurchaseGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radiopurchaseButton =findViewById(selectedId);
                if(radiopurchaseButton.getText().toString().equals("cash on delivery")) {
                    Map map=new HashMap();
                    map.put("Type","Cash on delivery");
                    databaseReference.child(Prevelent.userData.getUser_Name()).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            Intent intent=new Intent(Purchase_methods.this,byerHomeActivity.class);
                            startActivity(intent);
                        }
                    });

                }
                else  if(radiopurchaseButton.getText().toString().equals("Credit card")) {
                    Intent intent=new Intent(Purchase_methods.this,credit_card_payment.class);
                    startActivity(intent);
                }
            }

        });
        }
    }