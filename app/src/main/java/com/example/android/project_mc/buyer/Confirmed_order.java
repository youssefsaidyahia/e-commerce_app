package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.R;
import com.example.android.project_mc.purchase.Purchase_methods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Confirmed_order extends AppCompatActivity {
    TextView choose_map;
    EditText Name ,Email,Number,Adress;
    Button con;
    String checked="";
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_order);

        Name=findViewById(R.id.name);
        Email=findViewById(R.id.Email);
        Number=findViewById(R.id.p_number);
        Adress=findViewById(R.id.Home_address);
        choose_map=findViewById(R.id.choose_map);
        con=findViewById(R.id.confirm);
        price=getIntent().getExtras().getInt("totalPrice");
        choose_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked="true";
                Intent intent=new Intent(Confirmed_order.this,MapsActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validateData();
            }
        });
    }

    private void validateData() {
        if(Adress.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Address not found", Toast.LENGTH_LONG).show();
        }
        else if(Name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Your Name is missed",Toast.LENGTH_LONG).show();
        }
        else if(!Email.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Email is missed",Toast.LENGTH_LONG).show();
        }
        else if(Number.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"pLease , enter your number",Toast.LENGTH_LONG).show();
        }
        else{
            saveTODatabase();
        }

    }

    private void saveTODatabase() {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders");
        final Map map=new HashMap();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM ,yyyy");
        String currentDate=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HH:mm:ss a");
        String currentTime=simpleTimeFormat.format(calendar.getTime());

        map.put("Total_Price",price);
        map.put("Name",Name.getText().toString());
        map.put("Number",Number.getText().toString());
        map.put("Email",Email.getText().toString());
        map.put("Address",Adress.getText().toString());
        map.put("Time",currentTime);
        map.put("Date",currentDate);
        map.put("State","not shipped");
        map.put("UserName",Prevelent.userData.getUser_Name());
        databaseReference.child(Prevelent.userData.getUser_Name()).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Carts").child("Userview").child(Prevelent.userData.getUser_Name())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Cart List cleared",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Confirmed_order.this,Purchase_methods.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
