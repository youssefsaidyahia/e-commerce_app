package com.example.android.project_mc.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.productlistViewHolder;
import com.example.android.project_mc.buyer.Login_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerHomeActivity extends AppCompatActivity {
    RecyclerView notApprovedList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapterView;
    private ArrayList<Products> products=new ArrayList<>();
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent2=new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_addProduct:
                    Intent intent1=new Intent(SellerHomeActivity.this, Categorylist.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_Logout:
                    final FirebaseAuth  auth=FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent=new Intent(SellerHomeActivity.this, Login_activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        intializeview();
        getProductinfo();
    }

    private void getProductinfo() {
        Query databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sellerId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                             Products product1 = dataSnapshot1.getValue(Products.class);
                             products.add(product1);
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
        notApprovedList=findViewById(R.id.seller_products_list);
        notApprovedList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        notApprovedList.setLayoutManager(layoutManager);
        adapterView=new productlistViewHolder(products);
        notApprovedList.setAdapter(adapterView);
    }

}
