package com.example.android.project_mc.AdminFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.productViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminApprovedProductsActivity extends AppCompatActivity {
    RecyclerView notApprovedList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapterView;
    String type ;
    private DatabaseReference databaseReference;
    private ArrayList<Products> products=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approved_products);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            type=getIntent().getExtras().getString("User");
        }
        intializeview();
        getProductinfo();
    }
   Products product1;
    private void getProductinfo() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        product1 = dataSnapshot1.getValue(Products.class);
                        if (product1.getProductstate().equals("not Approved")) {
                            products.add(product1);

                        }
                    }
                }
                adapterView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void intializeview() {
        notApprovedList=findViewById(R.id.approved_products_list);
        notApprovedList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        notApprovedList.setLayoutManager(layoutManager);
        adapterView=new productViewHolder(products,type);
        notApprovedList.setAdapter(adapterView);
    }
}
