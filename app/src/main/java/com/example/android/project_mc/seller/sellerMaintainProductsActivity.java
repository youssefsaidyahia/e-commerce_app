package com.example.android.project_mc.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class sellerMaintainProductsActivity extends AppCompatActivity {
    private EditText name , description , price;
    private Button edit , delete;
    private String pid;
    ImageView view;
    private  DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        name=findViewById(R.id.item_Name);
        description=findViewById(R.id.item_Description);
        price=findViewById(R.id.item_price);
        edit=findViewById(R.id.edit);
        view=findViewById(R.id.item_Image);
        delete=findViewById(R.id.delete);
        pid=getIntent().getExtras().getString("pid");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child(pid);
        getData();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProduct();
            }
        });
    }

    private void DeleteProduct() {
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(sellerMaintainProductsActivity.this,SellerHomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void changeData() {
        final Map map=new HashMap();
       
        map.put("ProducName",name.getText().toString());
        map.put("ProductDescription",description.getText().toString());
        map.put("ProductSalary",price.getText().toString());
        databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(sellerMaintainProductsActivity.this,Categorylist .class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    name.setText(products.getProducName());
                    description.setText(products.getProductDescription());
                    price.setText(products.getProductSalary());
                    Picasso.get().load(products.getProducImage()).into(view);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
